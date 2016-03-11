package darklight.amino.repository;

import darklight.amino.Page;

import java.util.List;

/**
 * Created by hongmiao.yu on 2016/3/10.
 */
public class LocalRepository implements Repository {

    @Override
    public List<Page> acquireDirty(String spider, int limit) {
        return null;
    }

    @Override
    public void save(String spider, Page page) {

    }

    @Override
    public List<Page> acquireFresh(String spider, int i) {
        return null;
    }
}
