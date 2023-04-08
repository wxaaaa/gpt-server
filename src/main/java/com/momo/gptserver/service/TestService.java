package com.momo.gptserver.service;

import com.momo.gptserver.invoker.HttpInvoker;
import com.momo.gptserver.invoker.OpenAiInvoker;
import com.momo.gptserver.module.HttpRequestDO;
import com.momo.gptserver.module.HttpResponseDO;
import com.momo.gptserver.module.OpenAiRequestDO;
import com.momo.gptserver.module.OpenAiResponseDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private HttpInvoker httpInvoker;

    @Resource
    private OpenAiInvoker openAiInvoker;

    public HttpResponseDO testHttpGet(HttpRequestDO httpRequestDO) {
        return httpInvoker.httpGet(httpRequestDO);
    }

    public HttpResponseDO testHttpPost(HttpRequestDO httpRequestDO) {
        return httpInvoker.httpPost(httpRequestDO);
    }

    public OpenAiResponseDO testOpenAiInvoke(OpenAiRequestDO openAiRequestDO) {
        return openAiInvoker.invokeChat(openAiRequestDO);
    }
}
