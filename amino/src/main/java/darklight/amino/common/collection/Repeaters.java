package darklight.amino.common.collection;

/**
 * Created by hongmiao.yu on 2016/3/14.
 */
public class Repeaters {

    public static void repeat(int times, Runnable runnable) {
        for (int i=0; i<times; i++) {
            runnable.run();
        }
    }
}
