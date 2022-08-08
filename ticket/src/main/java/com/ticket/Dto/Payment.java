package com.ticket.Dto;

import com.ticket.Dto.Enums.PaymentType;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Payment {

    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private PaymentType paymentType;


}
