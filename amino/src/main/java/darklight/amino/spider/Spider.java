package darklight.amino.spider;

import darklight.amino.engine.Page;
import darklight.amino.common.Switchable;

/**
 * Created by hongmiao.yu on 2016/3/9.
 */
public interface Spider extends Switchable {

    SpiderStats stats();

    void crawl(Page page);

    Page next();

    String name();

    void setConfig(SpiderConfig config);
}
