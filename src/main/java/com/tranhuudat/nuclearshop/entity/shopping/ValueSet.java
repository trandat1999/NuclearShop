package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Entity
@Table(name = "tbl_value_set", uniqueConstraints = {@UniqueConstraint(name = "uniqueKeyAndItem", columnNames = {"key","item_id"})})
@Data
public class ValueSet extends BaseEntity {
    @Column(name = "key")
    private String key;
    @Column(name = "value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
