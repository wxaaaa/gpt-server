package com.momo.gptserver.invoker;

import com.momo.gptserver.module.OpenAiRequestDO;
import com.momo.gptserver.module.OpenAiResponseDO;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenAiInvoker {

    private static final String DEFAULT_TOKEN = "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";

    public OpenAiResponseDO invokeChat(OpenAiRequestDO openAiRequestDO) {
        if (!openAiRequestDO.isValid()) {
            OpenAiResponseDO.fail("invalid invoke params");
        }
        String token = StringUtils.isEmpty(openAiRequestDO.getToken()) ? DEFAULT_TOKEN : openAiRequestDO.getToken();

        try {

            OpenAiService service = new OpenAiService(token, Duration.ofSeconds(30L));
            List<ChatMessage> chatMessageList = openAiRequestDO.getMessage().stream()
                    .map(e -> new ChatMessage(openAiRequestDO.getRole(), e))
                    .collect(Collectors.toList());
            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(chatMessageList)
                    .model(openAiRequestDO.getModel())
                    .build();

            StringBuilder sb = new StringBuilder();
            service.createChatCompletion(completionRequest).getChoices().forEach(sb::append);
            OpenAiResponseDO openAiResponseDO = new OpenAiResponseDO();
            openAiResponseDO.setSuccess(true);
            openAiResponseDO.setOriginResult(sb.toString());
            return openAiResponseDO;
        } catch (Exception e) {
            return OpenAiResponseDO.fail("invoke error, error info: " + e.getMessage());
        }
    }
}
