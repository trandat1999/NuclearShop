package com.tranhuudat.nuclearshop.entity;

import com.tranhuudat.nuclearshop.type.TypeNotification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "topic")
    private String topic;

    @Column(name = "date_notification")
    private Date dateNotification;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeNotification type;
}
