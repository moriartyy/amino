package darklight.amino.spider;

import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.io.Closeables;
import darklight.amino.engine.Page;
import darklight.amino.http.HttpClient;
import darklight.amino.repository.Repository;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hongmiao.yu on 2016/3/15.
 */
public abstract class HtmlSpider extends AbstractSpider {

    public HtmlSpider(Settings settings, Environment environment, Repository repository, HttpClient httpClient) {
        super(settings, environment, repository, httpClient);
    }

    @Override
    public void crawl(Page page) {

        CloseableHttpResponse response = null;
        String content;
        try {
            response = httpClient.get(page.uri(), getHeaders(), getSocketTimeout(), getConnTimeout());

            content = EntityUtils.toString(response.getEntity());

            Document doc = Jsoup.parse(content);

            doc.body().getElementsByTag("a")
                    .forEach(element -> {
                        String url = normalize(element.attr("href"));
                        Page linkPage = Page.of(url, element.text(), element.attr("title"));
                        if (isValidPage(linkPage)) {
                            Page check = this.repository.get(linkPage.url());
                            if (check != null) {
                                linkPage.merge(check);
                            }
                            this.repository.save(this.name(), linkPage);
                        }

                    });

            

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(response);
        }
    }

    protected abstract int getSocketTimeout();

    protected abstract int getConnTimeout();

    protected abstract Map<String, String> getHeaders();

    protected abstract String normalize(String url);

    private List<Page> extractLinkPages(Page page) {
        return null;
    }

}
