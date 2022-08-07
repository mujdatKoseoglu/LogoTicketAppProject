package com.notification.Listener;

import com.notification.Dto.NotificationDto;
import com.notification.Model.Email;
import com.notification.Repository.EmailRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotification implements Notification {

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public void saveMessage(NotificationDto notificationDto) {
        Email email=new Email();
        email.setTo(notificationDto.getTo());
        email.setTitle(notificationDto.getTitle());
        email.setText(notificationDto.getText());
        emailRepository.save(email);
    }

}
