package com.momo.gptserver.module;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class HttpResponseDO {
    private boolean success;
    private String message;
    private Object originResult;
    private Map<String, Object> resultDataMap = Maps.newHashMap();

    public static HttpResponseDO fail(String message) {
        HttpResponseDO httpResponseDO = new HttpResponseDO();
        httpResponseDO.setMessage(message);
        httpResponseDO.setSuccess(false);
        return httpResponseDO;
    }
}
