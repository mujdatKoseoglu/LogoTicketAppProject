package com.notification.Listener;

import com.notification.Dto.NotificationDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @Autowired
    private MessageStrategy messageStrategy;

    @RabbitListener(queues = "ticket.information")
    public void notificationListener(NotificationDto notificationDto) {
        messageStrategy.getStrategy(notificationDto.getType()).saveMessage(notificationDto);
    }
}
