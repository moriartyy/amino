package darklight.amino.repository;

import com.google.inject.name.Names;
import darklight.amino.Module;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class RepositoryModule extends Module {

    @Override
    protected void configure() {
        bind(Repository.class).annotatedWith(Names.named("local")).to(LocalRepository.class).asEagerSingleton();
    }
}
