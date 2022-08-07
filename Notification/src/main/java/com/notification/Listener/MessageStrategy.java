package com.notification.Listener;


import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
@Service
public class MessageStrategy {
    static Map<String,Notification> strategyMap=new HashMap<>();
    static {
        strategyMap.put("Email",new EmailNotification());
        strategyMap.put("Sms",new SmsNotification());

    }
    public Notification getStrategy(String type) {

        return strategyMap.get(type);
    }
}
