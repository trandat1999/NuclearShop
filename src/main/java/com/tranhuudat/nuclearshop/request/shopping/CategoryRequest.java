package com.tranhuudat.nuclearshop.request.shopping;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CategoryRequest {
    private Long id;

    @NotNull(message = "{nuclearshop.validation.NotNull}")
    private String name;

    @NotNull(message = "{nuclearshop.validation.NotNull}")
    private String code;
    private String description;
    private Long parentId;
}
