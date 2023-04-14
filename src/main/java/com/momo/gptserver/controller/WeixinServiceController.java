package com.momo.gptserver.controller;

import com.momo.gptserver.module.AnalyseResultDTO;
import com.momo.gptserver.service.WeixinService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/weixin")
public class WeixinServiceController {
    @Resource
    private WeixinService weixinService;

    @PostMapping("askGPT")
    public AnalyseResultDTO askGPT(@RequestParam String msg,
                                        @RequestParam String promptName,
                                        @RequestBody(required = false) Map<String, String> extendParam) {
        return weixinService.analyseMessage(msg, promptName, extendParam);
    }
}
