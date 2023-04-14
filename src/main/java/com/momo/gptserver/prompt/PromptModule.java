package com.momo.gptserver.prompt;

import com.alibaba.fastjson.JSONObject;
import com.momo.gptserver.module.AnalyseResultDTO;

import java.util.List;
import java.util.Map;

public interface PromptModule {

    String promptName();

    String promptTemplate();

    default String process(Map<String, String> params) {
        return promptTemplate();
    }

    default AnalyseResultDTO generateResult(List<JSONObject> openAiResultData) {
        return null;
    }
}
