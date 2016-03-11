package darklight.amino.spider;

import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.component.LifecycleComponent;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public abstract class AbstractSpider extends LifecycleComponent implements Spider {

    public AbstractSpider(Settings settings, Environment environment) {
        super(settings, environment);
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
