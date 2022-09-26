package com.tranhuudat.nuclearshop.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    private Object body;
    private String message;
    private String status;
    private Integer code;
}
