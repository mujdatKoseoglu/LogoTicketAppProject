package com.ticket.Dto;

import com.ticket.Model.User;
import com.ticket.Model.Voyage;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketResponseDto {
    private Integer id;
    private Voyage voyage;
    private User user;
}
