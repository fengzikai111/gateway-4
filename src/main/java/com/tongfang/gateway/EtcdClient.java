package com.tongfang.gateway;

import com.google.common.base.Splitter;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.tongfang.gateway.etcdv2.EtcdNode;
import com.tongfang.gateway.etcdv2.EtcdResult;
import com.tongfang.gateway.util.Config;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class EtcdClient {
    private static Logger LOGGER = LoggerFactory.getLogger(EtcdClient.class);
    private static Config CONFIG = Config.getInstance();
    private static EtcdClient INSTANCE;

    private CloseableHttpAsyncClient httpClient;
    private final URI baseUri;

    public static EtcdClient getInstance() {
        if (INSTANCE == null) INSTANCE = new EtcdClient(URI.create(CONFIG.getEtcdV2Server()));
        return INSTANCE;
    }

    public EtcdClient(URI baseUri) {
        String uri = baseUri.toString();
        if (!uri.endsWith("/")) {
            uri += "/";
            baseUri = URI.create(uri);
        }
        this.baseUri = baseUri;

        httpClient = HttpAsyncClients.custom().setDefaultRequestConfig(RequestConfig.custom().build()).build();
        httpClient.start();
    }

    /**
     * Retrieves a key. Returns null if not found.
     */
    public EtcdResult get(String key) {
        try {
            return asyncExecutes(new HttpGet(buildKeyUri("v2/keys", key, ""))).get();
        } catch (InterruptedException e) {
            LOGGER.warn("interrupt exception async exec ", e);
            e.printStackTrace();
        } catch (ExecutionException e) {
            LOGGER.error("execution exception ", e);
            e.printStackTrace();
        }
        return null;
    }

    protected ListenableFuture<EtcdResult> asyncExecutes(HttpUriRequest request) {
        return Futures.transform(asyncExecuteHttp(request), (HttpResponse resp) -> {
            StatusLine statusLine = resp.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == HttpStatus.SC_OK && resp.getEntity() != null) {
                try {
                    return Const.GSON.fromJson(EntityUtils.toString(resp.getEntity()), EtcdResult.class);
                } catch (IOException e) {
                    LOGGER.warn("response error with code {}", statusCode, e);
                    throw new RuntimeException("Error reading response", e);
                }
            } else if (statusCode == HttpStatus.SC_NOT_FOUND) {
                LOGGER.debug("not fund result with status 404");
                return null;
            }

            LOGGER.warn("response error with code {}", statusCode);
            throw new RuntimeException("response error");
        });
    }

    private URI buildKeyUri(String prefix, String key, String suffix) {
        URI uri = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(prefix);
            if (key.startsWith("/")) {
                key = key.substring(1);
            }
            for (String token : Splitter.on('/').split(key)) {
                sb.append("/");
                sb.append(URLEncoder.encode(token, "UTF-8"));
            }
            sb.append(suffix);

            uri = baseUri.resolve(sb.toString());

            LOGGER.debug("build key uri is {}", uri.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("encode path unsupport encoding exceptin", e);
        }
        return uri;
    }

    protected ListenableFuture<HttpResponse> asyncExecuteHttp(HttpUriRequest request) {
        final SettableFuture<HttpResponse> future = SettableFuture.create();

        httpClient.execute(request, new FutureCallback<HttpResponse>() {
            public void completed(HttpResponse result) {
                future.set(result);
            }

            public void failed(Exception ex) {
                future.setException(ex);
            }

            public void cancelled() {
                future.setException(new InterruptedException());
            }
        });

        return future;
    }

    public Map<String, String> getSidPathMap(List<String> fidIndex) {
        Map<String, String> sidPathMap = new HashMap<>();

        fidIndex.forEach(index -> {
            EtcdResult result = get(index);
            LOGGER.debug("result is {}",Const.GSON.toJson(result));
            if (result != null) {
                List<EtcdNode> sidList = result.getNode().nodes;
                if (sidList != null) {
                    LOGGER.trace("get sid list {} by fidIndex {}", Const.GSON.toJson(sidList), index);
                    LOGGER.debug("get sid size {} by fidIndex {}", sidList.size(), index);

                    for (EtcdNode sidNode : sidList) {
                        if (sidNode.dir) {
                            String paths[] = sidNode.key.split("/");
                            sidPathMap.put(paths[paths.length - 1], sidNode.key);
                        }
                    }

                }
            }
        });

        return sidPathMap;
    }


    public List<String> queryFidIndexByGid(String gid) {
        EtcdResult cPayLoad = get(Const.COLLECTORS);
        List<String> selfFidIndex = new ArrayList<>();

        if (cPayLoad != null) {
            String fidsStr = cPayLoad.getNode().value;
            LOGGER.trace("query fids by gid response is {}", fidsStr);

            String[] collectorsPaths = fidsStr.split(",");
            LOGGER.debug("query fids length by response is {}", collectorsPaths.length);

            for (int i = 0; i < collectorsPaths.length; i++) {
                if (collectorsPaths[i].contains(gid)) {
                    String[] pathItems = collectorsPaths[i].split("/");

                    if (pathItems.length > 2) {
                        LOGGER.trace("query fid index {} by gid  {}", collectorsPaths[i], gid);
                        selfFidIndex.add(collectorsPaths[i]);
                    }
                }
            }
        }

        return selfFidIndex;
    }
}
