package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Entity
@Table(name = "tbl_product_import")
@Data
public class ProductImport extends BaseEntity {
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    @Column(name = "price")
    private Double price;
    @Column(name = "quantity")
    private Long quantity;
    @JoinColumn(name = "order_import_id")
    @ManyToOne
    private OrderImport orderImport;
}
