package com.momo.gptserver.service;

import com.momo.gptserver.invoker.OpenAiInvoker;
import com.momo.gptserver.module.AnalyseResultDTO;
import com.momo.gptserver.module.OpenAiRequestDO;
import com.momo.gptserver.module.OpenAiResponseDO;
import com.momo.gptserver.prompt.PromptBuildService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeixinService {
    private static final String ANALYSE_PROMPT = "Parse the text, then output a JSON object in the following format:{活动: { \"游泳\" | \"打游戏\" | \"睡觉\" | \"旅游\" | \"美食\"},  情感: { \"积极\" | \"消极\"},情感程度: 1-10}";

    @Resource
    private OpenAiInvoker openAiInvoker;

    @Resource
    private PromptBuildService promptBuildService;

    public AnalyseResultDTO analyseMessage(String msg, String promptName, Map<String, String> extendParam) {
        AnalyseResultDTO analyseDTO = new AnalyseResultDTO();
        OpenAiRequestDO openAiRequestDO = new OpenAiRequestDO();
        List<String> inputMsgList = new ArrayList<>();
        openAiRequestDO.setMessage(inputMsgList);

        String prompt = promptBuildService.buildPrompt(promptName, extendParam);
        if (StringUtils.isEmpty(prompt)) {
            analyseDTO.setSuccess(false);
            analyseDTO.setErrMsg("参数错误");
            return analyseDTO;
        }

        inputMsgList.add(prompt);
        inputMsgList.add(msg);
        OpenAiResponseDO openAiResponseDO = openAiInvoker.invokeChat(openAiRequestDO);

        // 解析
        if (CollectionUtils.isEmpty(openAiResponseDO.getResultData())) {
            analyseDTO.setSuccess(false);
            analyseDTO.setErrMsg("gpt请求失败");
            return analyseDTO;
        }

        // 只拿第一个msg
        return promptBuildService.generateResult(promptName, openAiResponseDO);
    }
}
