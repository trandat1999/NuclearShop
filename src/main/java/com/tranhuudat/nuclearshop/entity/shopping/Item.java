package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author DatNuclear on 28/12/2022
 * @project NuclearShop
 */
@Entity
@Table(name = "tbl_item")
@Data
public class Item extends BaseEntity {
    @OneToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;
    @Column(name="price")
    private Double price;
    @Column(name="discount")
    private Double discount;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "item",orphanRemoval = true)
    private List<ValueSet> valueSets = new ArrayList<>();
}
