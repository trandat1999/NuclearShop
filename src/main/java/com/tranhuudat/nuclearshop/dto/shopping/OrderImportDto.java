package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.OrderImport;
import com.tranhuudat.nuclearshop.entity.shopping.ProductImport;
import com.tranhuudat.nuclearshop.type.OrderImportStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link com.tranhuudat.nuclearshop.entity.shopping.OrderImport} entity
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderImportDto extends BaseDto {
    private Long id;
    private Date orderDate;
    private String staffOrder;
    private OrderImportStatus status;
    private String staffFinished;
    private Date dateFinished;
    private WarehouseDto warehouse;
    private PublisherDto publisher;
    private List<ProductImportDto> products = new ArrayList<>();

    public OrderImportDto(OrderImport entity) {
        if(!ObjectUtils.isEmpty(entity)){
            this.id = entity.getId();
            this.orderDate = entity.getOrderDate();
            this.staffOrder = entity.getStaffOrder();
            this.status = entity.getStatus();
            this.staffFinished = entity.getStaffFinished();
            this.dateFinished = entity.getDateFinished();
            if(!CollectionUtils.isEmpty(entity.getProducts())){
                products.clear();
                for(ProductImport productImport : entity.getProducts()){
                    products.add(new ProductImportDto(productImport));
                }
            }
        }
    }
}