package darklight.amino;

import com.google.common.base.Throwables;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hongmiao.yu on 2016/3/8.
 */
public class Page {

    private final URL url;
    private String spider;

    public Page(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw Throwables.propagate(e);
        }
    }

    public URL url() {
        return url;
    }

    public String spider() {
        return spider;
    }

    public void spider(String spider) {
        this.spider = spider;
    }
}
