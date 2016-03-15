package darklight.amino.engine;

import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import org.apache.http.entity.ContentType;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

/**
 * Created by hongmiao.yu on 2016/3/8.
 */
public class Page {

    private final URL url;
    private final URI uri;
    private Set<String> anchorText = Sets.newHashSet();
    private Set<String> anchorTitle = Sets.newHashSet();
    private String spider;
    private ContentType contentType;

    public Page(String url) {
        try {
            this.url = new URL(url);
            this.uri = this.url.toURI();
        } catch (MalformedURLException e) {
            throw Throwables.propagate(e);
        } catch (URISyntaxException e) {
            throw Throwables.propagate(e);
        }
    }

    public Page(String url, String anchorText, String anchorTitle) {
        this(url);
        this.anchorText.add(anchorText);
        this.anchorTitle.add(anchorTitle);
    }

    public URI uri() {
        return uri;
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

    public ContentType contentType() {
        return contentType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Set<Page> linkPages() {
        return null;
    }

    public static Page of(String url, String anchorText, String anchorTitle) {
        return new Page(url, anchorText, anchorTitle);
    }

    public void merge(Page check) {

    }
}
