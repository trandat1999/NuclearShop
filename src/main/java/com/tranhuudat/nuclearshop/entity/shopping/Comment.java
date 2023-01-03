package com.tranhuudat.nuclearshop.entity.shopping;

import com.tranhuudat.nuclearshop.entity.BaseEntity;
import com.tranhuudat.nuclearshop.entity.File;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author DatNuclear on 30/12/2022
 * @project NuclearShop
 */
@Data
@Entity
@Table(name ="tbl_comment")
public class Comment extends BaseEntity {
    @Column(name = "star")
    private Integer star;
    @Column(name = "content")
    @Lob
    private String content;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "tbl_comment_file",
            joinColumns = {@JoinColumn(name = "comment_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")})
    private Set<File> files = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Comment parent;
    @OneToMany(orphanRemoval = true,mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
