package darklight.amino;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import darklight.amino.common.CommonModule;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.repository.RepositoryModule;
import darklight.amino.spider.SpiderModule;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class Modules {

    public static Injector createInjector(Settings settings, Environment environment) {
        ImmutableList<Module> modules = ImmutableList.<Module>builder()
                .add(new CommonModule(settings, environment))
                .add(new RepositoryModule())
                .add(new SpiderModule())
                .build();

        return Guice.createInjector(modules);
    }

}
