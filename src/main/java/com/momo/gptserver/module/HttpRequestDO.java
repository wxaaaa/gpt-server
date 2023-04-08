package com.momo.gptserver.module;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;

@Data
public class HttpRequestDO {
    private String protocol;
    private String host;
    private String port;
    private String path;
    private Map<String, Object> headerMap = Maps.newHashMap();
    private Map<String, Object> requestParamMap = Maps.newHashMap();

    public boolean isValid() {
        return !StringUtils.isEmpty(host) && !StringUtils.isEmpty(port);
    }

    public static HttpRequestDO asHttp() {
        HttpRequestDO httpRequestDO = new HttpRequestDO();
        httpRequestDO.setProtocol("http");
        return httpRequestDO;
    }

    public static HttpRequestDO asHttps() {
        HttpRequestDO httpRequestDO = new HttpRequestDO();
        httpRequestDO.setProtocol("https");
        return httpRequestDO;
    }
}
