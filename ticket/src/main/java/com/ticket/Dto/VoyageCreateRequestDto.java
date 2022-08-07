package com.ticket.Dto;

import com.ticket.Model.Enums.City;
import com.ticket.Model.Enums.Vehicle;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoyageCreateRequestDto {
    private City destinationCity;
    private City targetCity;
    private Vehicle vehicle;
    private LocalDateTime localDateTime;
    private BigDecimal price;
    private Integer adminId;
    private Integer capacity;

}
