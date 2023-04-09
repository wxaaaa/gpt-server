package com.momo.gptserver.service;

import com.alibaba.fastjson.JSONObject;
import com.momo.gptserver.invoker.OpenAiInvoker;
import com.momo.gptserver.module.AnalyseDTO;
import com.momo.gptserver.module.OpenAiRequestDO;
import com.momo.gptserver.module.OpenAiResponseDO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeixinService {
    private static final String ANALYSE_PROMPT = "Parse the text, then output a JSON object in the following format:{活动: { \"游泳\" | \"打游戏\" | \"睡觉\" | \"旅游\" | \"美食\"},  情感: { \"积极\" | \"消极\"},情感程度: 1-10}";

    @Resource
    private OpenAiInvoker openAiInvoker;

    public AnalyseDTO analyseMessage(String msg) {
        AnalyseDTO analyseDTO = new AnalyseDTO();
        OpenAiRequestDO openAiRequestDO = new OpenAiRequestDO();
        List<String> inputMsgList = new ArrayList<>();
        openAiRequestDO.setMessage(inputMsgList);

        inputMsgList.add(ANALYSE_PROMPT);
        inputMsgList.add(msg);
        OpenAiResponseDO openAiResponseDO = openAiInvoker.invokeChat(openAiRequestDO);

        // 解析
        if (CollectionUtils.isEmpty(openAiResponseDO.getResultData())) {
            return analyseDTO;
        }

        // 只拿第一个msg
        JSONObject jsonObject = openAiResponseDO.getResultData().get(0);
        JSONObject activityAnalyse = jsonObject.getJSONObject("活动");
        activityAnalyse.forEach((k, v) -> {
            if (((boolean) v)) {
                analyseDTO.getAnalyseActivity().add(k);
            }
        });

        JSONObject emotionAnalyse = jsonObject.getJSONObject("情感");
        emotionAnalyse.forEach((k, v) -> {
            if (((boolean) v)) {
                analyseDTO.setAnalyseEmotion(k);
            }
        });

        Integer score = jsonObject.getInteger("情感程度");
        analyseDTO.setScore(score);
        analyseDTO.setOriginResponse(openAiResponseDO.getOriginResult());
        return analyseDTO;
    }
}
