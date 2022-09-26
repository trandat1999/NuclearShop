package com.tranhuudat.nuclearshop.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tbl_role")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "role_name", length = 150, nullable = false)
    private String name;
    @Column(name = "role_description", length = 512)
    private String description;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
