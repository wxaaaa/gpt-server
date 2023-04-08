package com.momo.gptserver.controller;

import com.momo.gptserver.module.HttpRequestDO;
import com.momo.gptserver.module.HttpResponseDO;
import com.momo.gptserver.module.OpenAiRequestDO;
import com.momo.gptserver.module.OpenAiResponseDO;
import com.momo.gptserver.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestServiceController {
    @Resource
    private TestService testService;

    @PostMapping("testHttpGet")
    public HttpResponseDO testHttpGet(@RequestBody HttpRequestDO httpRequestDO){
        return testService.testHttpGet(httpRequestDO);
    }

    @PostMapping("testHttpPost")
    public HttpResponseDO testHttpPost(@RequestBody HttpRequestDO httpRequestDO){
        return testService.testHttpPost(httpRequestDO);
    }

    @PostMapping("testOpenAiInvoke")
    public OpenAiResponseDO testOpenAiInvoke(@RequestBody OpenAiRequestDO openAiRequestDO){
        return testService.testOpenAiInvoke(openAiRequestDO);
    }
}
