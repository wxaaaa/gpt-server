package com.momo.gptserver.module;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
public class OpenAiRequestDO {
    private String role = "user";
    private List<String> message = new ArrayList<>();
    private String token;
    private String model = "gpt-3.5-turbo";

    public boolean isValid() {
        return !CollectionUtils.isEmpty(message) && !StringUtils.isEmpty(model);
    }
}
