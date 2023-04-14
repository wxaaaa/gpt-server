package com.momo.gptserver.prompt.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.momo.gptserver.module.AnalyseResultDTO;
import com.momo.gptserver.prompt.PromptModule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ActivityPromptModule implements PromptModule {
//    @Value("${default_emotionactivity}")
    private static final String defaultActivity = "爬山,健身,阅读,游泳,听音乐,做瑜伽,做饭,看电影,看电视,看书,看新闻,看体育比赛,野餐,睡觉,逛街购物,网购,写作,聊天,打卡拉OK,打保龄球,打高尔夫球,打篮球,打台球,打网球,打游戏,打桥牌,打麻将,滑雪,滑板,滑冰,滑旱冰,爬楼梯,跑步,走路,骑自行车,钓鱼,飞行,飞天扬帆,阅读报纸,阅读杂志,采摘水果,音乐会,音乐剧,骑马,瑜伽,游乐园,游艇,游泳,游戏,演唱会";
//
//    @Value("${default_emotion}")
    private static final String defaultEmotion = "积极,消极";

    private static final List<String> DEFAULT_ACTIVITY_LIST = Arrays.asList(defaultActivity.split(","));
    private static final List<String> DEFAULT_EMOTION_LIST = Arrays.asList(defaultEmotion.split(","));

    @Override
    public String promptName() {
        return "activity";
    }

    @Override
    public String promptTemplate() {
        return "Parse the text, then output a JSON object in the following format:{\"activity\": [activityList], \"emotion\": [emotionTypeList], \"emotionScore\": emotionScore}, activityList data is in [${activity}], emotionTypeList should guess from [${emotion}], emotionScore need between 1 and 10";
    }

    @Override
    public String process(Map<String, String> params) {
        List<String> activityList = new ArrayList<>();
        if (params.containsKey("activityList")) {
            String customActivityListStr = params.get("activityList");
            activityList.addAll(Arrays.asList(customActivityListStr.split(",")));
        } else {
            activityList.addAll(DEFAULT_ACTIVITY_LIST);
        }

        List<String> emotionList = new ArrayList<>();
        if (params.containsKey("emotionList")) {
            String customEmotionListStr = params.get("emotionList");
            emotionList.addAll(Arrays.asList(customEmotionListStr.split(",")));
        } else {
            emotionList.addAll(DEFAULT_EMOTION_LIST);
        }

        String activityVal = activityList.stream()
                .map(e -> String.format("\"%s\"", e))
                .collect(Collectors.joining(","));
        String emotionVal = emotionList.stream()
                .map(e -> String.format("\"%s\"", e))
                .collect(Collectors.joining(","));

        String replacedVal =
                promptTemplate().replace("${activity}", activityVal)
                .replace("${emotion}", emotionVal);

        return replacedVal;
    }

    @Override
    public AnalyseResultDTO generateResult(List<JSONObject> openAiResultData) {
        AnalyseResultDTO analyseDTO = new AnalyseResultDTO();
        JSONObject jsonObject = openAiResultData.get(0);
        JSONArray activityAnalyseList = jsonObject.getJSONArray("activity");
        activityAnalyseList.forEach(e -> {
                analyseDTO.getAnalyseActivity().add((String) e);
        });

        JSONArray emotionAnalyseList = jsonObject.getJSONArray("emotion");
        emotionAnalyseList.forEach(e -> {
            analyseDTO.getAnalyseEmotion().add((String) e);
        });

        Integer score = jsonObject.getInteger("emotionScore");
        analyseDTO.setScore(score);
        return analyseDTO;
    }

    //    @Value("${gpt.default-activity}")
//    public void setDefaultActivity(String defaultActivity) {
//        DEFAULT_ACTIVITY_LIST.addAll(Arrays.asList(defaultActivity.split(",")));
//    }
//
//    @Value("${gpt.default-emotion}")
//    public void setDefaultEmotion(String defaultEmotion) {
//        DEFAULT_EMOTION_LIST.addAll(Arrays.asList(defaultEmotion.split(",")));
//    }

    public static void main(String[] args) {
        ActivityPromptModule activityPromptModule = new ActivityPromptModule();
        String str = "{\n" +
                "  \"activity\": [\"爬山\", \"健身\"],\n" +
                "  \"emotion\": [\"积极\"],\n" +
                "  \"emotionScore\": 8\n" +
                "}";
        JSONObject jsonObject = JSON.parseObject(str);
        AnalyseResultDTO analyseResultDTO = activityPromptModule.generateResult(Arrays.asList(jsonObject));
        System.out.println(JSON.toJSONString(analyseResultDTO));
    }
}
