package com.tranhuudat.nuclearshop.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordRequest {

    @NotNull(message = "Please enter a valid email address")
    @Email(message = "Please enter your email address")
    private String email;

    @NotNull(message = "Username cannot be null")
    private String username;
}
