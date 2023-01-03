package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.Warehouse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

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
    private String name;
    private String code;
    private Double acreage;
    private String description;
    private String phoneNumber;
    private String address;
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