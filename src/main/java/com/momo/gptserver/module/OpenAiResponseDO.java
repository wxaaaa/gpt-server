package com.momo.gptserver.module;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class OpenAiResponseDO {
    private boolean success;
    private String message;
    private String originResult;
    private List<JSONObject> resultData = new ArrayList<>();

    public static OpenAiResponseDO fail(String message) {
        OpenAiResponseDO httpResponseDO = new OpenAiResponseDO();
        httpResponseDO.setMessage(message);
        httpResponseDO.setSuccess(false);
        return httpResponseDO;
    }
}
