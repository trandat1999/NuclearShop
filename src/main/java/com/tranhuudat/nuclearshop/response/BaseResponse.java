package com.tranhuudat.nuclearshop.response;


import com.tranhuudat.nuclearshop.type.YesNoStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<T> {
    private T body;
    private String message;
    private YesNoStatus code;
}
