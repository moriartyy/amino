package darklight.amino.common.component;

import com.google.common.collect.Lists;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.Switchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public abstract class LifecycleComponent implements Switchable {

    protected final Settings settings;
    protected final Environment environment;
    protected final List<LifecycleListener> listeners = Lists.newCopyOnWriteArrayList();
    protected Logger logger;

    public LifecycleComponent(Settings settings, Environment environment) {
        this.settings = settings;
        this.environment = environment;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    public void addListener(LifecycleListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(LifecycleListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void start() {
        this.logger.info("{} staring...", this.getClass().getSimpleName());
        this.listeners.forEach(l -> l.beforeStart());
        this.doStart();
        this.listeners.forEach(l -> l.afterStart());
        this.logger.info("{} started", this.getClass().getSimpleName());
    }

    @Override
    public void stop() {
        this.logger.info("{} stopping...", this.getClass().getSimpleName());
        this.listeners.forEach(l -> l.beforeStop());
        this.doStop();
        this.listeners.forEach(l -> l.afterStop());
        this.logger.info("{} stopped", this.getClass().getSimpleName());
    }

    protected abstract void doStart();
    protected abstract void doStop();
}
