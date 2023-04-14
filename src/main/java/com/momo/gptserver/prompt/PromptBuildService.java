package com.momo.gptserver.prompt;

import com.momo.gptserver.module.AnalyseResultDTO;
import com.momo.gptserver.module.OpenAiResponseDO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PromptBuildService implements ApplicationContextAware {

    private Map<String, PromptModule> moduleMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, PromptModule> promptModuleMap = applicationContext.getBeansOfType(PromptModule.class);
        promptModuleMap.forEach((k, v) -> {
            moduleMap.put(v.promptName(), v);
        });
    }

    public String buildPrompt(String promptName, Map<String, String> params) {
        if (!moduleMap.containsKey(promptName)) {
            return null;
        }
        PromptModule promptModule = moduleMap.get(promptName);
        return promptModule.process(params);
    }

    public AnalyseResultDTO generateResult(String promptName, OpenAiResponseDO openAiResponseDO) {
        if (!moduleMap.containsKey(promptName)) {
            return null;
        }
        PromptModule promptModule = moduleMap.get(promptName);
        AnalyseResultDTO analyseResultDTO = promptModule.generateResult(openAiResponseDO.getResultData());
        analyseResultDTO.setOriginResponse(openAiResponseDO.getOriginResult());
        return analyseResultDTO;
    }
}
