package com.ticket.Repository;

import com.ticket.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findTicketsByUserIdAndVoyageId(Integer userId,Integer voyageId);

}
