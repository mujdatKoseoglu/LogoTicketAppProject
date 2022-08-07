package com.notification.Listener;

import com.notification.Dto.NotificationDto;
import com.notification.Model.Email;
import com.notification.Model.Sms;
import com.notification.Repository.SmsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SmsNotification implements Notification {

    @Autowired
    private SmsRepository smsRepository;

    @Override
    public void saveMessage(NotificationDto notificationDto) {
        Sms sms=new Sms();
        sms.setTo(notificationDto.getTo());
        sms.setTitle(notificationDto.getTitle());
        sms.setText(notificationDto.getText());
        smsRepository.save(sms);
    }
}
