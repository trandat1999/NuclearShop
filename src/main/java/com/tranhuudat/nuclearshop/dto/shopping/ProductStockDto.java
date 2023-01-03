package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.ProductStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * A DTO for the {@link com.tranhuudat.nuclearshop.entity.shopping.ProductStock} entity
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductStockDto extends BaseDto {
    private Long id;
    private Long quantity;
    private WarehouseDto warehouse;
    private ProductDto product;
    private OrderImportDto orderImport;
    public ProductStockDto(ProductStock entity) {
        if(!ObjectUtils.isEmpty(entity)){
            this.id = entity.getId();
            this.quantity = entity.getQuantity();
            if(!ObjectUtils.isEmpty(entity.getOrderImport())){
                this.orderImport = new OrderImportDto(entity.getOrderImport());
            }
        }
    }
}