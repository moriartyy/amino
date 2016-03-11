package darklight.amino.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hongmiao.yu on 2016/3/10.
 */
public class Environment {

    private final static Logger logger = LoggerFactory.getLogger(Environment.class);

    private final Settings settings;
    private final File homeDir;
    private final File configDir;
    private final File workDir;
    private final File logsDir;
    private final File spidersDir;

    public Environment(Settings settings) {

        this.settings = settings;

        if (settings.get("path.home") != null) {
            this.homeDir = new File(settings.get("path.home"));
        } else if (settings.get("system.user.dir") != null) {
            this.homeDir = new File(settings.get("system.user.dir"));
        } else {
            this.homeDir = new File(System.getProperty("user.dir"));
        }
        logger.trace("amino home is [{}]", homeDir);

        if (settings.get("path.conf") != null) {
            this.configDir = new File(settings.get("path.conf"));
        } else {
            this.configDir = new File(this.homeDir, "config");
        }

        if (settings.get("path.conf") != null) {
            this.workDir = new File(settings.get("path.work"));
        } else {
            this.workDir = new File(this.homeDir, "work");
        }

        if (settings.get("path.logs") != null) {
            this.logsDir = new File(settings.get("path.logs"));
        } else {
            this.logsDir = new File(this.homeDir, "logs");
        }

        if (settings.get("path.spiders") != null) {
            this.spidersDir = new File(settings.get("path.spiders"));
        } else {
            this.spidersDir = new File(this.homeDir, "spiders");
        }


    }

    public Settings getSettings() {
        return settings;
    }

    public File homeDir() {
        return homeDir;
    }

    public File configDir() {
        return configDir;
    }

    public File workDir() {
        return workDir;
    }

    public File logsDir() {
        return logsDir;
    }

    public File spidersDir() {
        return spidersDir;
    }

    public URL resolveConfig(String configFile) {
        File file = new File(this.configDir, configFile);
        if (file.exists()) {
            try {
                return file.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new SettingsException("Failed to resolve path [" + file + "]", e);
            }
        } else {
            throw new SettingsException("Config file not exist [" + file + "]");
        }
    }
}
