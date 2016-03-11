package darklight.amino.spider;

import darklight.amino.Module;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class SpiderModule extends Module {

    @Override
    protected void configure() {
        bind(SpidersManager.class);
    }
}
