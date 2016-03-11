package darklight.amino.common;

import darklight.amino.Module;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class CommonModule extends Module {

    private final Settings settings;
    private final Environment environment;

    public CommonModule(Settings settings, Environment environment) {
        this.settings = settings;
        this.environment = environment;
    }

    @Override
    protected void configure() {
        bind(Settings.class).toInstance(settings);
        bind(Environment.class).toInstance(environment);
    }
}
