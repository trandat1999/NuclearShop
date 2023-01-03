package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import com.tranhuudat.nuclearshop.type.OrderImportStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Entity
@Table(name="tbl_order_import")
@Data
public class OrderImport extends BaseEntity {
    @Column(name="order_date")
    private Date orderDate;
    @Column(name="staff_order")
    private String staffOrder;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private OrderImportStatus status;
    @Column(name="staff_finished")
    private String staffFinished;
    @Column(name="date_finished")
    private Date dateFinished;
    @ManyToOne
    @JoinColumn(name="warehouse_id")
    private Warehouse warehouse;
    @ManyToOne
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy="orderImport", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductImport> products = new ArrayList<>();
}
