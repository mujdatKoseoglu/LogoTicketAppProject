package com.ticket.Service;

import com.ticket.Dto.TicketCreateRequestDto;
import com.ticket.Exception.TicketNotFoundException;
import com.ticket.Model.Ticket;
import com.ticket.Model.User;
import com.ticket.Model.Voyage;
import com.ticket.Repository.TicketRepository;
import com.ticket.Repository.UserRepository;
import com.ticket.Repository.VoyageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private VoyageRepository voyageRepository;

    @Mock
    private UserRepository userRepository;


    @Test
    void getByTicketIdTest(){
        when(ticketRepository.findById(5)).thenReturn(Optional.of(Ticket.builder().id(5).build()));
        Ticket ticket = ticketService.getByTicketId(5);
        assertEquals(5, ticket.getId());

    }

    @Test
    void whenNotFindGetById_ShouldThrowTicketNotFoundExceptionTest() {
        when(ticketRepository.findById(5)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> ticketService.getByTicketId(5))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessageContaining("bilet bulunamadı");
    }

    @Test
    void getTotalSaleNumberTest(){
        Mockito.when(ticketRepository.findAll().size()).thenReturn(Mockito.any());
    }

    /*@Test
    void getTotalPriceTest(){
        List<Ticket> ticketList = (List<Ticket>) Mockito.when(ticketRepository.findAll()).thenReturn(Mockito.anyList());
        Optional<BigDecimal> bigDecimal=ticketList.stream().map(t->t.getVoyage().getPrice()).reduce(BigDecimal::add);
        BigDecimal bigDecimal1=ticketService.getTotalPrice();
        assertEquals(bigDecimal, bigDecimal1);
    }*/

    @Test
    void createTicketTest(){
        TicketCreateRequestDto ticketCreateRequestDto=TicketCreateRequestDto.builder().userId(5).voyageId(5).build();
        Voyage voyage=Voyage.builder().id(5).build();
        User user=User.builder().id(5).build();
        given(voyageRepository.findById(ticketCreateRequestDto.getVoyageId())).willReturn(Optional.of(voyage));
        given(userRepository.findById(ticketCreateRequestDto.getUserId())).willReturn(Optional.of(user));
        Ticket ticket=ticketService.createTicket(ticketCreateRequestDto);
        ticket.setUser(user);
        ticket.setVoyage(voyage);

        verify(ticketRepository).save(Mockito.any());
        assertEquals(ticket.getUser().getId(),ticketCreateRequestDto.getUserId());
    }
}
