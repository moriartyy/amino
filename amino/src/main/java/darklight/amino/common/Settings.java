package darklight.amino.common;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Closeables;
import org.elasticsearch.common.Classes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hongmiao.yu on 2016/3/10.
 */
public class Settings {

    private final ImmutableMap<String, String> settings;

    public static Settings SYSTEM_SETTINGS = builder().put("system.", System.getProperties()).build();
    private ClassLoader classLoader;

    private Settings(Map<String, String> settings, ClassLoader classLoader) {
        this.settings = ImmutableMap.copyOf(settings);
        this.classLoader = classLoader;
    }

    public String get(String key) {
        return this.settings.get(key);
    }

    public Map<String, String> getAsMap() {
        return settings;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ClassLoader getClassLoader() {
        return this.classLoader == null ? Classes.getDefaultClassLoader() : classLoader;
    }

    public static class Builder {

        private final Map<String, String> map = Maps.newHashMap();
        private ClassLoader classLoader;

        public Builder put(Properties settings) {
            settings.forEach((k, v) -> map.put((String) k, (String) v));
            return this;
        }

        public Builder put(String prefix, Properties settings) {
            settings.forEach((k, v) -> map.put(prefix + (String) k, (String) v));
            return this;
        }

        public Builder loadFromUrl(URL url) {
            InputStream inputStream = null;
            try {
                inputStream = url.openStream();
                Properties properties = new Properties();
                properties.load(inputStream);
                return put(properties);
            } catch (IOException e) {
                throw new SettingsException("Failed to open stream for url [" + url.toExternalForm() + "]", e);
            } finally {
                Closeables.closeQuietly(inputStream);
            }
        }

        public Builder put(Settings settings) {
            map.putAll(settings.getAsMap());
            return this;
        }

        public Builder put(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        public Settings build() {
            return new Settings(map, classLoader);
        }


    }


}
