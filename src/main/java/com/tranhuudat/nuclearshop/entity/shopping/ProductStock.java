package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Entity
@Table(name = "tbl_product_stock")
@Data
public class ProductStock extends BaseEntity {
    @Column(name = "quantity")
    private Long quantity;
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_import_id")
    private OrderImport orderImport;
}
