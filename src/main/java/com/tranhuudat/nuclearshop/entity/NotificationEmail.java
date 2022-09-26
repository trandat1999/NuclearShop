package com.tranhuudat.nuclearshop.entity;

import com.tranhuudat.nuclearshop.type.TypeEmail;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_mail_notification")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "subject", length = 500)
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeEmail type;

    @Column(name = "link")
    private String link;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
}
