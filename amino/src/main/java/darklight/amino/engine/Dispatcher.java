package darklight.amino.engine;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.component.LifecycleComponent;
import darklight.amino.spider.SpidersManager;

import java.util.LinkedList;

/**
 * Created by hongmiao.yu on 2016/3/10.
 */
public class Dispatcher extends LifecycleComponent {

    private final SpidersManager spidersManager;
    private LinkedList<Page> fetchingQueue = Lists.newLinkedList();

    @Inject
    public Dispatcher(Settings settings, Environment environment, SpidersManager spidersManager) {
        super(settings, environment);
        this.spidersManager = spidersManager;
    }

    public synchronized Page next() {
        Page page = fetchingQueue.poll();
        if (page == null) {
            this.spidersManager.spiders().values().forEach(spider -> fetchingQueue.push(spider.next()));
            page = this.fetchingQueue.poll();
        }
        return page;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
