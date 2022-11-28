package com.tranhuudat.nuclearshop.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Pattern(regexp = "[a-zA-Z0-9]+", message = "username only characters and numbers")
    @NotNull(message = "username cannot be null")
    private String username;

    private String password;

    @NotNull(message = "Please enter your email address")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "enabled cannot be null")
    private Boolean enabled;

    @NotNull(message = "roles cannot be null")
    @NotEmpty(message = "roles cannot be empty")
    private Set<RoleRequest> roles;

    @Valid
    @NotNull(message = "person cannot be null")
    private PersonRequest person;

}
