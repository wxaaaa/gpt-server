package com.momo.gptserver.module;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AnalyseResultDTO extends ResultBaseDTO{
    private List<String> analyseActivity = new ArrayList<>();
    private List<String> AnalyseEmotion = new ArrayList<>();
    private Integer score;

    private String originResponse;
}
