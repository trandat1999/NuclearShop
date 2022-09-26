package com.tranhuudat.nuclearshop.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    @NotNull(message = "role name must not be null")
    @NotBlank(message = "role name must not be empty")
    private String name;

    private String description;

    private Long id;
}
