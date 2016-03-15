package darklight.amino.engine;

import com.google.inject.Injector;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.Switchable;
import darklight.amino.common.collection.Repeaters;
import darklight.amino.spider.SpidersManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by hongmiao.yu on 2016/3/9.
 */
public class Engine implements Switchable {

    private final Settings settings;
    private final Environment environment;
    private final Injector injector;
    private final Dispatcher dispatcher;
    private final SpidersManager spidersManager;
    private final int workerNumber;
    private volatile boolean running;
    private ExecutorService workerThreadPool;

    public Engine(Settings settings, Environment environment) {
        this.settings = settings;
        this.environment = environment;
        this.injector = Modules.createInjector(settings, environment);
        this.dispatcher = injector.getInstance(Dispatcher.class);
        this.spidersManager = injector.getInstance(SpidersManager.class);
        this.workerNumber = settings.getAsInt("engine.worker_number", Runtime.getRuntime().availableProcessors() * 2);
        this.workerThreadPool = Executors.newFixedThreadPool(this.workerNumber, new WorkerThreadFactory());

    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        this.injector.getInstance(SpidersManager.class).start();
        this.injector.getInstance(Dispatcher.class).start();
        Repeaters.repeat(this.workerNumber, () -> this.workerThreadPool.execute(new WorkerRunnable()));
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        this.workerThreadPool.shutdownNow();
        this.injector.getInstance(SpidersManager.class).stop();
        this.injector.getInstance(Dispatcher.class).start();
    }

    public EngineStats stats() {
        EngineStats engineStats = new EngineStats();
        return engineStats;
    }

    class WorkerRunnable implements Runnable {

        @Override
        public void run() {
            while (running) {
                Page page = dispatcher.next();
                if (page == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    spidersManager.fetch(page);
                }
            }
        }
    }

    class WorkerThreadFactory implements ThreadFactory {

        private int workNumber;

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("worker-thread-#" + workNumber++);
            t.setDaemon(true);
            return t;
        }
    }
}
