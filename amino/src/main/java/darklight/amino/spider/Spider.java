package darklight.amino.spider;

import darklight.amino.Page;
import darklight.amino.common.Switchable;

/**
 * Created by hongmiao.yu on 2016/3/9.
 */
public interface Spider extends Switchable {

    SpiderStats stats();

    void fetch(Page page);

    Page next();

    String name();
}
