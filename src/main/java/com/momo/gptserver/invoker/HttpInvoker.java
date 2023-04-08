package com.momo.gptserver.invoker;

import com.google.gson.Gson;
import com.momo.gptserver.module.HttpRequestDO;
import com.momo.gptserver.module.HttpResponseDO;
import okhttp3.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

@Service
public class HttpInvoker {
    private static Logger LOG = Logger.getLogger(HttpInvoker.class);

    private static final Gson GSON = new Gson();

    public HttpResponseDO httpGet(HttpRequestDO httpRequestDO) {
        if (Objects.isNull(httpRequestDO) || !httpRequestDO.isValid()) {
            return HttpResponseDO.fail("invalid http request");
        }
        OkHttpClient client = new OkHttpClient();
        String url = buildUrlPrefix(httpRequestDO);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return HttpResponseDO.fail("reponse failed, " + response.message());
            }
            HttpResponseDO httpResponseDO = new HttpResponseDO();
            String json = response.body().string();
            Map<String, Object> resultMap = GSON.fromJson(json, Map.class);
            httpResponseDO.setSuccess(true);
            httpResponseDO.setOriginResult(response.body());
            httpResponseDO.setResultDataMap(resultMap);
            return httpResponseDO;
        } catch (Exception e) {
            LOG.error("[HttpInvoker] send get request error", e);
            return HttpResponseDO.fail(e.getMessage());
        }
    }

    public HttpResponseDO httpPost(HttpRequestDO httpRequestDO) {
        if (Objects.isNull(httpRequestDO) || !httpRequestDO.isValid()) {
            return HttpResponseDO.fail("invalid http request");
        }

        String jsonData = GSON.toJson(httpRequestDO.getRequestParamMap());
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonData
        );

        String url = buildUrlPrefix(httpRequestDO);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return HttpResponseDO.fail("reponse failed, " + response.message());
            }
            HttpResponseDO httpResponseDO = new HttpResponseDO();
            String json = response.body().string();
            Map<String, Object> resultMap = GSON.fromJson(json, Map.class);
            httpResponseDO.setSuccess(true);
            httpResponseDO.setOriginResult(response.body());
            httpResponseDO.setResultDataMap(resultMap);
            return httpResponseDO;
        } catch (Exception e) {
            LOG.error("[HttpInvoker] send get request error", e);
            return HttpResponseDO.fail(e.getMessage());
        }
    }

    private String buildUrlPrefix(HttpRequestDO httpRequestDO) {
        StringBuilder url = new StringBuilder();
        url.append(httpRequestDO.getProtocol()).append("://").append(httpRequestDO.getHost());
        if (!StringUtils.isEmpty(httpRequestDO.getPort())) {
            url.append(":").append(httpRequestDO.getPort());
        }
        if (!StringUtils.isEmpty(httpRequestDO.getPath())) {
            url.append(httpRequestDO.getPath().startsWith("/") ? "" : "/").append(httpRequestDO.getPath());
        }
        return url.toString();
    }
}
