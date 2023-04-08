package com.momo.gptserver.module;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class OpenAiResponseDO {
    private boolean success;
    private String message;
    private Object originResult;
    private Map<String, Object> resultDataMap = Maps.newHashMap();

    public static OpenAiResponseDO fail(String message) {
        OpenAiResponseDO httpResponseDO = new OpenAiResponseDO();
        httpResponseDO.setMessage(message);
        httpResponseDO.setSuccess(false);
        return httpResponseDO;
    }
}
