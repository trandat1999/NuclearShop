package com.tranhuudat.nuclearshop.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {
    @NotNull(message = "refreshToken must not be null")
    private String refreshToken;
    @NotNull(message = "username must not be null")
    private String username;
}
