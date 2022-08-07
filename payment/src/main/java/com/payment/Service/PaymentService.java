package com.payment.Service;

import com.payment.Model.Payment;
import com.payment.Repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Integer paymentId) {
        Payment payment1=paymentRepository.findById(paymentId).get();
        return payment1;
    }
}
