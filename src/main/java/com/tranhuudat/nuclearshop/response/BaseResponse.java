package com.tranhuudat.nuclearshop.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BaseResponse implements Serializable {
    private Object body;
    private String message;
    private String status;
    private Integer code;
}
