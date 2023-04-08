package com.momo.gptserver.controller;

import com.momo.gptserver.module.HttpRequestDO;
import com.momo.gptserver.module.HttpResponseDO;
import com.momo.gptserver.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestServiceController {
    @Resource
    private TestService testService;

    @PostMapping("testHttpInvoke")
    public HttpResponseDO getSysInfo(@RequestBody HttpRequestDO httpRequestDO){
        return testService.testHttpGet(httpRequestDO);
    }
}
