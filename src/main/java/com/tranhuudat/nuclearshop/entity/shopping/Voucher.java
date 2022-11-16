package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_voucher")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Voucher extends BaseEntity {
}
