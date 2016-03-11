package darklight.amino.common.component;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public interface LifecycleListener {

    void beforeStart();

    void afterStart();

    void beforeStop();

    void afterStop();
}
