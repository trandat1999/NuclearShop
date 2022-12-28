package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.dto.FileDto;
import com.tranhuudat.nuclearshop.entity.File;
import com.tranhuudat.nuclearshop.entity.shopping.Category;
import com.tranhuudat.nuclearshop.entity.shopping.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto extends BaseDto {
    private Long id;
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
    private List<FileDto> files;
    @NotEmpty(message = "{nuclear.shop.validation.NotEmpty}")
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private List<CategoryDto> categories;
    public ProductDto(Product entity) {
        if (!ObjectUtils.isEmpty(entity)) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.shortDescription = entity.getShortDescription();
            this.description = entity.getDescription();
            if (!CollectionUtils.isEmpty(entity.getCategories())) {
                this.categories = new ArrayList<>();
                for (Category category : entity.getCategories()) {
                    this.categories.add(new CategoryDto(category));
                }
            }
            if (!CollectionUtils.isEmpty(entity.getFiles())) {
                this.files = new ArrayList<>();
                for (File file : entity.getFiles()) {
                    this.files.add(new FileDto(file));
                }
            }
        }
    }
}
