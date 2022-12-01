package com.tranhuudat.nuclearshop.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("")
public class RestNotificationController extends BaseRestController{
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/notify")
    public String getNotification() {
        // Push notifications to front-end
        template.convertAndSend("/topic/notification", Math.random());
        return "Notifications successfully sent to Angular !";
    }

    @MessageMapping("/topic/notification")
    public String getTopNotification(@Payload String message, Principal principal) {
        System.out.println(principal.getName());
        template.convertAndSend("/topic/notification", Math.random());
        return "Notifications successfully sent to Angular !";
    }
}
