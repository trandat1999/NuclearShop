package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.Publisher;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.tranhuudat.nuclearshop.entity.shopping.Publisher} entity
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Data
@NoArgsConstructor
public class PublisherDto extends BaseDto {
    private Long id;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    @NotBlank(message = "{nuclear.shop.validation.NotBlank}")
    private String name;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    @NotBlank(message = "{nuclear.shop.validation.NotBlank}")
    private String code;
    private String description;
    private String address;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private AdministrativeUnitDto administrativeUnit;

    public PublisherDto(Publisher entity){
        if(!ObjectUtils.isEmpty(entity)){
            this.id = entity.getId();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.description = entity.getDescription();
            this.address = entity.getAddress();
            if(!ObjectUtils.isEmpty(entity.getAdministrativeUnit())){
                this.administrativeUnit = new AdministrativeUnitDto(entity.getAdministrativeUnit());
            }
        }
    }
}