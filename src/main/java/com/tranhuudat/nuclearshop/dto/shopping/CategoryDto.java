package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class CategoryDto extends BaseDto {
    private Long id;
    @NotNull(message = "{nuclearshop.validation.NotNull}")
    private String name;
    @NotNull(message = "{nuclearshop.validation.NotNull}")
    private String code;
    private String description;
    private Long parentId;
    public CategoryDto(Category entity){
        if(!ObjectUtils.isEmpty(entity)){
            this.id = entity.getId();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            this.parentId = entity.getParentId();
        }
    }
}
