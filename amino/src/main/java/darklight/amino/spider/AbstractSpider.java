package darklight.amino.spider;

import com.google.common.collect.Lists;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.component.LifecycleComponent;
import darklight.amino.engine.Page;
import darklight.amino.http.HttpClient;
import darklight.amino.repository.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public abstract class AbstractSpider extends LifecycleComponent implements Spider {

    protected final Repository repository;
    protected final LinkedList<Page> fetchQueue = Lists.newLinkedList();
    protected final HttpClient httpClient;
    protected SpiderConfig config;

    public AbstractSpider(Settings settings, Environment environment, Repository repository, HttpClient httpClient) {
        super(settings, environment);
        this.repository = repository;
        this.httpClient = httpClient;

    }

    public void setConfig(SpiderConfig config) {
        this.config = config;
    }

    protected Set<Page> getSeeds() {
        return config.seeds();
    }

    @Override
    protected void doStart() {
        getSeeds().forEach(fetchQueue::add);
    }

    @Override
    protected void doStop() {
        this.fetchQueue.clear();
    }

    @Override
    public SpiderStats stats() {
        return null;
    }


    protected abstract boolean isValidPage(Page page);

    @Override
    public Page next() {
        Page page = fetchQueue.poll();
        if (page == null) {

            List<Page> freshPages = this.repository.acquireFresh(this.name(), 1000);
            freshPages.forEach(p -> {
                if (isValidPage(p)) {
                    this.fetchQueue.add(p);
                } else {
                    this.repository.delete(p);
                }
            });

            List<Page> dirtyPages = this.repository.acquireDirty(this.name(), 1000);
            dirtyPages.forEach(p -> {
                if (isValidPage(p)) {
                    this.fetchQueue.add(p);
                } else {
                    this.repository.delete(p);
                }
            });

            page = fetchQueue.poll();
        }
        return page;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
