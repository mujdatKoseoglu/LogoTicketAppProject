package com.ticket.Controller;

import com.ticket.Dto.TicketCreateRequestDto;
import com.ticket.Dto.TicketDto;
import com.ticket.Dto.TicketResponseDto;
import com.ticket.Model.Ticket;
import com.ticket.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public Ticket createTicket(@RequestBody TicketCreateRequestDto ticketCreateRequestDto){
        return ticketService.createTicket(ticketCreateRequestDto);
    }

    @GetMapping("/{ticketId}")
    public Ticket getByTicketId(@PathVariable Integer ticketId){
        return ticketService.getByTicketId(ticketId);
    }
    //toplam bilet satışı
    @GetMapping("/total-sale")
    public Integer getTotalSaleNumber(){
        return ticketService.getTotalSaleNumber();
    }
    //toplam bilet satış tutarı
    @GetMapping("/total-price")
    public BigDecimal getTotalPrice(){
        return ticketService.getTotalPrice();
    }

    @PostMapping("create-all")
    public List<Ticket> createTicketAll(@RequestBody List<TicketCreateRequestDto> ticketCreateRequestDtoList){
        return ticketService.createTicket(ticketCreateRequestDtoList);
    }

}
