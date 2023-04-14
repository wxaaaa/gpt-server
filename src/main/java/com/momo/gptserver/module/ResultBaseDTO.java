package com.momo.gptserver.module;

import lombok.Data;

@Data
public class ResultBaseDTO {
    private boolean success = true;
    private String errCode;
    private String errMsg;
}
