package darklight.amino.repository;

import darklight.amino.engine.Page;

import java.net.URL;
import java.util.List;

/**
 * Created by hongmiao.yu on 2016/3/10.
 */
public interface Repository {

    List<Page> acquireDirty(String spider, int limit);

    void save(String spider, Page page);

    List<Page> acquireFresh(String spider, int i);

    void delete(Page page);

    Page get(URL url);
}
