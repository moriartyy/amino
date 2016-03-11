package darklight.amino.common.io;

import java.io.Closeable;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class Closeables {

    public static void safelyClose(Closeable... objects) {
        for (Closeable object : objects) {
            try {
                if (object != null) {
                    object.close();
                }
            } catch (Throwable t) {
            }
        }
    }
}
