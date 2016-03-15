package darklight.amino.http;

import com.google.inject.Inject;
import darklight.amino.common.Environment;
import darklight.amino.common.Settings;
import darklight.amino.common.component.LifecycleComponent;
import darklight.amino.common.io.Closeables;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * Created by hongmiao.yu on 2016/3/14.
 */
public class HttpClient extends LifecycleComponent {

    private CloseableHttpClient client;
    private PoolingHttpClientConnectionManager connectionManager;
    private final static Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private final static int DEFAULT_SOCKET_TIEMOUT = 3000;
    private final static int DEFAULT_CONNECTION_TIMEOUT = 4000;
    private final static int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 6000;
    private final static int DEFAULT_MAX_TOTAL_CONNECTION = 1000;
    private final static int DEFAUTL_MAX_CONNECTION_PER_ROUTE = 10;

    @Inject
    public HttpClient(Settings settings, Environment environment) {
        super(settings, environment);

        this.connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTION);
        connectionManager.setDefaultMaxPerRoute(DEFAUTL_MAX_CONNECTION_PER_ROUTE);
    }

    public void setMaxConnection(String host, int port, int max) {
        this.connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(host, port)), max);
    }

    public CloseableHttpResponse head(URI uri, Map<String, String> headers) throws IOException {
        RequestBuilder builder = RequestBuilder.head();
        for(Map.Entry<String, String> header : headers.entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        builder.setUri(uri);
        builder.setConfig(RequestConfig.DEFAULT);
        return this.client.execute(builder.build());
    }

    public CloseableHttpResponse get(URI uri, Map<String, String> headers, int socketTimeout, int connTimeout) throws IOException {
        RequestBuilder builder = RequestBuilder.get();
        for(Map.Entry<String, String> header : headers.entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        builder.setUri(uri);
        builder.setConfig(RequestConfig
                .custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connTimeout)
                .build());
        return this.client.execute(builder.build());
    }

    @Override
    protected void doStart() {
        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setConnectionManager(connectionManager);
        this.client = clientBuilder.build();
    }

    @Override
    protected void doStop() {
        Closeables.closeQuietly(this.client);
    }

}
