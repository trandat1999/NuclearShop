package com.tranhuudat.nuclearshop.entity;

import com.tranhuudat.nuclearshop.type.TypeNotification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "tbl_notification")
@Data
@SuperBuilder
@RequiredArgsConstructor
public class Notification extends BaseEntity{

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeNotification type;
}
