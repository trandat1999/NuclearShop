package com.tranhuudat.nuclearshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BaseEntity extends AuditableEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voided")
    private Boolean voided;
}
