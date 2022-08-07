package com.notification.Repository;

import com.notification.Model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SmsRepository extends JpaRepository<Sms,Integer> {
}
