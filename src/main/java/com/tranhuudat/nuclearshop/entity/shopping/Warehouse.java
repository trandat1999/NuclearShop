package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import com.tranhuudat.nuclearshop.util.anotation.PhoneNumber;
import lombok.Data;

import javax.persistence.*;

/**
 * @author DatNuclear on 30/12/2022
 * @project NuclearShop
 */
@Data
@Table(name = "tbl_warehouse")
@Entity
public class Warehouse extends BaseEntity {
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "code", nullable = false,unique = true)
    private String code;
    @Column(name = "acreage")
    private Double acreage;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @ManyToOne
    @JoinColumn(name = "administrative_unit_id")
    private AdministrativeUnit administrativeUnit;
}
