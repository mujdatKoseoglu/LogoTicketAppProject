package com.ticket.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TicketCreateRequestDto {
    private Integer userId;
    private Integer voyageId;
}
