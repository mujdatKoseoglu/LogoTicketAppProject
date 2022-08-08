package com.ticket.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TicketCreateRequestDto {
    private Integer userId;
    private Integer voyageId;
}
