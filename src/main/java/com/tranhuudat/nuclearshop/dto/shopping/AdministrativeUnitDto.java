package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
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
}
