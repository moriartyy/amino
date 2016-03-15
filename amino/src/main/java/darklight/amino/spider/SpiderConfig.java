package darklight.amino.spider;

import darklight.amino.engine.Page;

import java.util.Properties;
import java.util.Set;

/**
 * Created by hongmiao.yu on 2016/3/15.
 */
public class SpiderConfig {

    private SpiderConfig(Properties spiderProps) {

    }

    public static SpiderConfig of(Properties spiderProps) {
        return new SpiderConfig(spiderProps);
    }

    public Set<Page> seeds() {
        return null;
    }
}
