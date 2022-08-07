package com.payment.Controller;

import com.payment.Model.Payment;

import com.payment.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping(value = "/createPayment")
    Payment createPayment(@RequestBody Payment payment){
        return paymentService.createPayment(payment);
    }

    @GetMapping("/paymentId")
    Payment getPaymentById(@PathVariable Integer paymentId){
        return paymentService.getPaymentById(paymentId);
    }
}
