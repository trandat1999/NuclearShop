package com.tranhuudat.nuclearshop.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message ="username must not be null")
    private String username;

    @NotNull(message ="password must not be null")
    private String password;
}
