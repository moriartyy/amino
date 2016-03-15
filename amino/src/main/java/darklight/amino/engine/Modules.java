package darklight.amino.engine;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.repository.LocalRepository;
import darklight.amino.repository.Repository;
import darklight.amino.spider.SpidersManager;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class Modules {

    public static Injector createInjector(Settings settings, Environment environment) {
//        ImmutableList<Module> modules = ImmutableList.<Module>builder()
//                .add(new CommonModule(settings, environment))
//                .add(new RepositoryModule())
//                .add(new SpiderModule())
//                .build();
//
//        return Guice.createInjector(modules);

        Module module = new Module() {

            @Override
            protected void configure() {
                bind(Settings.class).toInstance(settings);
                bind(Environment.class).toInstance(environment);
                bind(Repository.class).annotatedWith(Names.named("local")).to(LocalRepository.class).asEagerSingleton();
                bind(SpidersManager.class);
                bind(Dispatcher.class);
            }
        };

        return Guice.createInjector(module);
    }

}
