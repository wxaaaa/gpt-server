package com.momo.gptserver.invoker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.momo.gptserver.module.ChatCompletionOptionDO;
import com.momo.gptserver.module.OpenAiRequestDO;
import com.momo.gptserver.module.OpenAiResponseDO;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpenAiInvoker {

    public OpenAiResponseDO invokeChat(OpenAiRequestDO openAiRequestDO) {
        ChatCompletionOptionDO optionDO = new ChatCompletionOptionDO();
        return invokeChat(openAiRequestDO, optionDO);
    }

    public OpenAiResponseDO invokeChat(OpenAiRequestDO openAiRequestDO, ChatCompletionOptionDO optionDO) {
        if (!openAiRequestDO.isValid()) {
            OpenAiResponseDO.fail("invalid invoke params");
        }
        String token = StringUtils.isEmpty(openAiRequestDO.getToken()) ? System.getenv("OPENAPI_KEY") : openAiRequestDO.getToken();

        try {

            OpenAiService service = new OpenAiService(token, Duration.ofSeconds(30L));
            List<ChatMessage> chatMessageList = openAiRequestDO.getMessage().stream()
                    .map(e -> new ChatMessage(openAiRequestDO.getRole(), e))
                    .collect(Collectors.toList());
            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(chatMessageList)
                    .model(openAiRequestDO.getModel())
                    .temperature(optionDO.getTemperature())
                    .build();

            StringBuilder sb = new StringBuilder();
            List<JSONObject> resultData = new ArrayList<>();

            List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();
            choices.forEach(choice -> {
                sb.append(choice);
                String content = Optional.ofNullable(choice)
                        .map(ChatCompletionChoice::getMessage)
                        .map(ChatMessage::getContent)
                        .orElse(null);
                if (StringUtils.isEmpty(content)) {
                    return;
                }
                JSONObject jsonObject = JSON.parseObject(content);
                resultData.add(jsonObject);
            });


            OpenAiResponseDO openAiResponseDO = new OpenAiResponseDO();
            openAiResponseDO.setSuccess(true);
            openAiResponseDO.setOriginResult(sb.toString());
            openAiResponseDO.getResultData().addAll(resultData);
            return openAiResponseDO;
        } catch (Exception e) {
            return OpenAiResponseDO.fail("invoke error, error info: " + e.getMessage());
        }
    }
}
