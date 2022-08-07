package com.payment.Dto;

import com.payment.Model.Enums.PaymentType;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Integer id;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private PaymentType paymentType;
}
