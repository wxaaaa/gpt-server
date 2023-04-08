package com.momo.gptserver.controller;

import com.momo.gptserver.module.AnalyseDTO;
import com.momo.gptserver.service.WeixinService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/weixin")
public class WeixinServiceController {
    @Resource
    private WeixinService weixinService;

    @GetMapping("testHttpGet")
    public AnalyseDTO testHttpGet(@RequestParam String msg){
        return weixinService.analyseMessage(msg);
    }
}
