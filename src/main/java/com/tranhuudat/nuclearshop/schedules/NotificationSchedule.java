package com.tranhuudat.nuclearshop.schedules;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author DatNuclear 6/6/2023
 * @project NuclearShop
 */
@Component
@Slf4j
@ConditionalOnExpression("${application.scheduling.notifications.enabled}")
@AllArgsConstructor
public class NotificationSchedule {
    private final SimpMessagingTemplate template;
    @Scheduled(fixedDelay = 5000)
    public void autoScheduleNotification(){
        template.convertAndSendToUser("admin","/topic/notification","abcd");
    }
}
