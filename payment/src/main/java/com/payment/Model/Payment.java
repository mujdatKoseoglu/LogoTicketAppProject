package com.payment.Model;

import com.payment.Model.Enums.PaymentType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
