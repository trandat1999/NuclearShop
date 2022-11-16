package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_file_product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileProduct extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
