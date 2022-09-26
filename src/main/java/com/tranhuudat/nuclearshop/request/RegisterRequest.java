package com.tranhuudat.nuclearshop.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Pattern(regexp = "[a-zA-Z0-9]+", message = "username only characters and numbers")
    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Please enter a valid email address")
    @Email(message = "Please enter your email address")
    private String email;
}
