package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.ProductImport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

/**
 * A DTO for the {@link com.tranhuudat.nuclearshop.entity.shopping.ProductImport} entity
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductImportDto extends BaseDto {
    private Long id;
    private ProductDto product;
    private Double price;
    private Long quantity;
    private OrderImportDto orderImport;
    public ProductImportDto(ProductImport entity) {
        if(!ObjectUtils.isEmpty(entity)){
            this.id = entity.getId();
            this.price = entity.getPrice();
            this.quantity = entity.getQuantity();
            if(!ObjectUtils.isEmpty(entity.getProduct())){
                this.product = new ProductDto(entity.getProduct());
            }
        }
    }
}