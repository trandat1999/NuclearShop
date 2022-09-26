package com.tranhuudat.nuclearshop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tbl_file")
@Entity
@Getter
@Setter
public class File extends BaseEntity {
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "description")
    private String description;
}
