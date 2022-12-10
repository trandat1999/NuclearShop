package com.tranhuudat.nuclearshop.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tbl_user_notification")
@Data
@SuperBuilder
@RequiredArgsConstructor
public class UserNotification extends BaseEntity{

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "notification_id", nullable = false)
    private Long NotificationId;

    @Column(name = "is_read")
    private boolean read;

    @Column(name = "read_at")
    private Date readAt;
}
