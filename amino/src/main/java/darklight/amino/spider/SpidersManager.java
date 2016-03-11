package darklight.amino.spider;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Closeables;
import com.google.inject.Inject;
import com.google.inject.Injector;
import darklight.amino.common.AminoException;
import darklight.amino.common.ClassLoaders;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.component.LifecycleComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class SpidersManager extends LifecycleComponent {

    private final Map<String, Spider> spiders;
    private final static String SPIDER_PROPERTIES_FIEL = "amino-spider.properties";
    private final static Logger logger = LoggerFactory.getLogger(SpidersManager.class);

    @Inject
    public SpidersManager(Settings settings, Environment environment, Injector injector) {
        super(settings, environment);

        this.spiders = loadSpiders(injector);
    }

    private Map<String, Spider> loadSpiders(Injector injector) {
        ClassLoader classLoader = this.settings.getClassLoader();
        ClassLoaders.addClassPath(classLoader, environment.spidersDir());
        return loadSpidersFromSpidersDir(injector);
    }

    private Map<String, Spider> loadSpidersFromSpidersDir(Injector injector) {
        ImmutableMap.Builder<String, Spider> spiders = ImmutableMap.builder();

        try {
            Enumeration<URL> spiderURLs = settings.getClassLoader().getResources(SPIDER_PROPERTIES_FIEL);

            // use a set for uniqueness as some classloaders such as groovy's can return the same URL multiple times and
            // these plugins should only be loaded once
            HashSet<URL> uniqueUrls = new HashSet<>(Collections.list(spiderURLs));
            for (URL spiderUrl : uniqueUrls) {
                Properties spiderProps = new Properties();
                InputStream inputStream = null;
                try {
                    inputStream = spiderUrl.openStream();
                    spiderProps.load(inputStream);
                    String spiderClassName = spiderProps.getProperty("type");
                    Class<? extends Spider> spiderClass = (Class<? extends Spider>) settings.getClassLoader().loadClass(spiderClassName);
                    Spider spider = injector.getInstance(spiderClass);

                    //TODO 可能有重名的爬虫，需要处理
                    spiders.put(spider.name(), spider);
                } catch (Throwable e) {
                    logger.warn("failed to load spider from [" + spiderUrl + "]", e);
                } finally {
                    Closeables.closeQuietly(inputStream);
                }
            }
        } catch (IOException e) {
            logger.warn("failed to find jvm plugins from classpath", e);
        }

        return spiders.build();

    }

    public Map<String, Spider> spiders() {
        return this.spiders;
    }

    @Override
    protected void doStart() {
        this.spiders.values().forEach(spider -> spider.start());
    }

    @Override
    protected void doStop() {
        this.spiders.values().forEach(spider -> spider.stop());
    }
}
