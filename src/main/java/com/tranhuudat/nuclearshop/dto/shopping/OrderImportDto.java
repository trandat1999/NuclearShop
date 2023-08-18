package com.tranhuudat.nuclearshop.dto.shopping;

import com.tranhuudat.nuclearshop.dto.BaseDto;
import com.tranhuudat.nuclearshop.entity.shopping.OrderImport;
import com.tranhuudat.nuclearshop.entity.shopping.ProductImport;
import com.tranhuudat.nuclearshop.type.OrderImportStatus;
import com.tranhuudat.nuclearshop.util.anotation.NotNullIfAnotherFieldHasValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@NotNullIfAnotherFieldHasValue(
        fieldName = "status",
        fieldValue = OrderImportStatus.Constants.FINISHED,
        dependFieldName = "dateFinished")
public class OrderImportDto extends BaseDto {
    private Long id;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private Date orderDate;
    private String staffOrder;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private OrderImportStatus status;
    private String staffFinished;
    private Date dateFinished;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private WarehouseDto warehouse;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    private PublisherDto publisher;
    @NotNull(message = "{nuclear.shop.validation.NotNull}")
    @NotEmpty(message = "{nuclear.shop.validation.NotEmpty}")
    @Valid
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
            if(entity.getWarehouse()!=null){
                this.warehouse = new WarehouseDto(entity.getWarehouse());
            }
            if(entity.getPublisher()!=null){
                this.publisher = new PublisherDto(entity.getPublisher());
            }
        }
    }
}