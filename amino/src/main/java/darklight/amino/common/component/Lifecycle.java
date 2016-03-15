package darklight.amino.common.component;

import darklight.amino.common.Switchable;

/**
 * Created by hongmiao.yu on 2016/3/15.
 */
public interface Lifecycle extends Switchable {

    void addListener(LifecycleListener listener);
    void removeListener(LifecycleListener listener);
}
