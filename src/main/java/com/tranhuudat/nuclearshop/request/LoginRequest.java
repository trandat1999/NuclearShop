package com.tranhuudat.nuclearshop.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "{nuclearshop.validation.NotNull}")
    private String username;

    @NotNull(message = "password must not be null")
    private String password;
}
