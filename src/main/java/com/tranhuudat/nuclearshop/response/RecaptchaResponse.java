package com.tranhuudat.nuclearshop.response;

import lombok.Data;

@Data
public class RecaptchaResponse {
    private boolean success;
    private String hostname;
}
