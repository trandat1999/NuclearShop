package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.AdministrativeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AdministrativeUnitDto extends BaseDto {
    @NotNull(message = "{nuclearshop.validation.NotNull}")
    private String name;

    private String englishName;

    private String levelName;

    private String description;

    @NotNull(message = "{nuclearshop.validation.NotNull}")
    private String code;

    private Long id;

    private Integer level;

    private String parentCode;

    private AdministrativeUnitDto parent;
    private Set<AdministrativeUnitDto> children;
    public AdministrativeUnitDto(AdministrativeUnit entity){
        if(!ObjectUtils.isEmpty(entity)){
            this.id = entity.getId();
            this.code = entity.getCode();
            this.englishName = entity.getEnglishName();
            this.name = entity.getName();
            if(!ObjectUtils.isEmpty(entity.getParent())){
                this.parent = new AdministrativeUnitDto(entity.getParent());
            }
        }
    }
}
