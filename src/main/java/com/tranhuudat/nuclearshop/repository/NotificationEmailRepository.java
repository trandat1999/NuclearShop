package com.tranhuudat.nuclearshop.repository;

import com.tranhuudat.nuclearshop.entity.NotificationEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationEmailRepository extends JpaRepository<NotificationEmail,Long> {
}
