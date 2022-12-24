package com.tranhuudat.nuclearshop.request.shopping;

import com.tranhuudat.nuclearshop.request.FileRequest;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductRequest {

    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    @NotBlank(message = "{nuclear.shop.validation.NotBlank}")
    private String name;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    @NotBlank(message = "{nuclear.shop.validation.NotBlank}")
    private String code;
    private String description;

    private String shortDescription;

    private Boolean voided;

    @NotEmpty(message = "{nuclear.shop.validation.NotEmpty}")
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private List<FileRequest> files;

    @NotEmpty(message = "{nuclear.shop.validation.NotEmpty}")
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private List<CategoryRequest> categories;
}
