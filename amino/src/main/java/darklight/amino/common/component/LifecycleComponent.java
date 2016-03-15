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
public abstract class LifecycleComponent implements Lifecycle {

    protected final Settings settings;
    protected final Environment environment;
    protected final Listeners listeners = new Listeners();
    protected Logger logger;
    protected LifecycleState state = LifecycleState.UnStarted;

    public LifecycleComponent(Settings settings, Environment environment) {
        this.settings = settings;
        this.environment = environment;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void addListener(LifecycleListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(LifecycleListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void start() {
        if (!this.state.canMoveToStart()) {
            this.logger.info("Current in {} state, can not start.", this.state);
            return;
        }

        this.state = LifecycleState.Starting;
        this.logger.info("{} staring...", this.getClass().getSimpleName());
        this.listeners.beforeStart();
        this.doStart();
        this.listeners.afterStart();
        this.logger.info("{} started", this.getClass().getSimpleName());
        this.state = LifecycleState.Started;
    }

    @Override
    public void stop() {
        if (!this.state.canMoveToStop()) {
            this.logger.info("Current in {} state, can not stop.", this.state);
            return;
        }
        this.state = LifecycleState.Stopping;
        this.logger.info("{} stopping...", this.getClass().getSimpleName());
        this.listeners.beforeStop();
        this.doStop();
        this.listeners.afterStop();
        this.logger.info("{} stopped", this.getClass().getSimpleName());
        this.state = LifecycleState.Stopped;
    }

    protected abstract void doStart();
    protected abstract void doStop();

    class Listeners implements LifecycleListener {

        private final List<LifecycleListener> listeners = Lists.newCopyOnWriteArrayList();

        public void add(LifecycleListener listener) {
            this.listeners.add(listener);
        }

        public void remove(LifecycleListener listener) {
            this.listeners.remove(listener);
        }

        @Override
        public void beforeStart() {
            this.listeners.forEach(l -> {
                try {
                    l.afterStart();
                } catch (Exception e) {
                    logger.error("failed to invoke beforeStart method on listener.", e);
                }
            });
        }

        @Override
        public void afterStart() {
            this.listeners.forEach(l -> {
                try {
                    l.afterStart();
                } catch (Exception e) {
                    logger.error("failed to invoke afterStart method on listener.", e);
                }
            });
        }

        @Override
        public void beforeStop() {
            this.listeners.forEach(l -> {
                try {
                    l.afterStart();
                } catch (Exception e) {
                    logger.error("failed to invoke beforeStop method on listener.", e);
                }
            });
        }

        @Override
        public void afterStop() {
            this.listeners.forEach(l -> {
                try {
                    l.afterStart();
                } catch (Exception e) {
                    logger.error("failed to invoke afterStop method on listener.", e);
                }
            });
        }
    }
}
