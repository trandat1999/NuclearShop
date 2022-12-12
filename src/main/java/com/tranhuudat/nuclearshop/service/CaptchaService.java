package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.response.RecaptchaResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {
    private final RestTemplate restTemplate;

    public CaptchaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Value("${google.recaptcha.secret.key}")
    public String recaptchaSecret;

    @Value("${google.recaptcha.verify.url}")
    public String recaptchaVerifyUrl;

    public boolean verify(String response) {
        MultiValueMap<Object, Object> param= new LinkedMultiValueMap<>();
        param.add("secret", recaptchaSecret);
        param.add("response", response);
        try {
            RecaptchaResponse recaptchaResponse = this.restTemplate.postForObject(recaptchaVerifyUrl, param, RecaptchaResponse.class);
            if(recaptchaResponse==null){
               return false;
            }
            return recaptchaResponse.isSuccess();
        }catch(RestClientException e){
            return false;
        }
    }
}
