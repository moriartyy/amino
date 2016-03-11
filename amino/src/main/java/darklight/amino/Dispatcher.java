package darklight.amino;

import com.google.common.collect.Lists;
import darklight.amino.spider.Spider;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hongmiao.yu on 2016/3/10.
 */
public class Dispatcher {

    private final List<Spider> spiders;
    private LinkedList<Page> dispatchQueue = Lists.newLinkedList();

    public Dispatcher(List<Spider> spiders) {
        this.spiders = spiders;
    }

    public synchronized Page next() {
        Page page = dispatchQueue.poll();
        if (page == null) {
            this.spiders.forEach(spider -> dispatchQueue.push(spider.next()));
            page = this.dispatchQueue.poll();
        }
        return page;
    }
}
