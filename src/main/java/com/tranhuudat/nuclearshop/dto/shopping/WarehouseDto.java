package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.Warehouse;
import com.tranhuudat.nuclearshop.util.anotation.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 * A DTO for the {@link com.tranhuudat.nuclearshop.entity.shopping.Warehouse} entity
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Getter
@Setter
@NoArgsConstructor
public class WarehouseDto extends BaseDto {
    private Long id;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    @NotBlank(message = "{nuclear.shop.validation.NotBlank}")
    private String name;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    @NotBlank(message = "{nuclear.shop.validation.NotBlank}")
    private String code;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private Double acreage;
    private String description;
    @PhoneNumber(message = "{nuclear.shop.validation.PhoneNumber}")
    private String phoneNumber;
    private String address;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private AdministrativeUnitDto administrativeUnit;
    public WarehouseDto(Warehouse entity) {
        if(!ObjectUtils.isEmpty(entity)){
            this.id = entity.getId();
            this.name = entity.getName();
            this.code = entity.getCode();
            this.acreage = entity.getAcreage();
            this.description = entity.getDescription();
            this.phoneNumber = entity.getPhoneNumber();
            this.address = entity.getAddress();
            if(!ObjectUtils.isEmpty(entity.getAdministrativeUnit())){
                this.administrativeUnit = new AdministrativeUnitDto(entity.getAdministrativeUnit());
            }
        }
    }
}