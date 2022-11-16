package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_publisher")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publisher extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany
    private Set<Category> categories = new HashSet<>();
}
