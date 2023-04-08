package com.momo.gptserver.service;

import com.momo.gptserver.invoker.HttpInvoker;
import com.momo.gptserver.module.HttpRequestDO;
import com.momo.gptserver.module.HttpResponseDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    private HttpInvoker httpInvoker;

    public HttpResponseDO testHttpGet(HttpRequestDO httpRequestDO) {
        return httpInvoker.httpGet(httpRequestDO);
    }

    public HttpResponseDO testHttpPost(HttpRequestDO httpRequestDO) {
        return httpInvoker.httpPost(httpRequestDO);
    }
}
