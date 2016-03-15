package darklight.amino.common.io;

import java.io.Closeable;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class Closeables {

    public static void closeQuietly(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (Throwable t) {
            }
        }
    }
}
