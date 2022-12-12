package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_administrative_unit")
@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class AdministrativeUnit extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "code",unique = true)
    private String code;

    @Column(name = "level")
    private Integer level;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AdministrativeUnit parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<AdministrativeUnit> children;
}
