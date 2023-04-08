package com.momo.gptserver.module;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnalyseDTO {
    private List<String> analyseActivity = new ArrayList<>();
    private String AnalyseEmotion;
    private Integer score;

    private String originResponse;
}
