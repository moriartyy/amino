package darklight.amino;

import com.google.inject.Injector;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.Switchable;
import darklight.amino.spider.SpidersManager;

/**
 * Created by hongmiao.yu on 2016/3/9.
 */
public class Engine implements Switchable {

    private final Settings settings;
    private final Environment environment;
    private final Injector injector;

    public Engine(Settings settings, Environment environment) {
        this.settings = settings;
        this.environment = environment;
        this.injector = Modules.createInjector(settings, environment);
    }

    public void start() {
        this.injector.getInstance(SpidersManager.class).start();
    }

    public void stop() {
        this.injector.getInstance(SpidersManager.class).stop();
    }

    public EngineStats stats() {
        EngineStats engineStats = new EngineStats();
        return engineStats;
    }
}
