package com.tranhuudat.nuclearshop.request.shopping;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryRequest {
    private Long id;

    @NotNull(message = "{nuclear.shop.validation.field.not.null}")
    private String name;

    @NotNull(message = "{nuclear.shop.validation.field.not.null}")
    private String code;
    private String description;
    private Long parentId;
}
