package com.ticket.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ticket.Model.Enums.City;
import com.ticket.Model.Enums.Vehicle;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voyages")
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private City destinationCity;

    @Enumerated(EnumType.STRING)
    private City targetCity;

    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private Vehicle vehicle;

    private BigDecimal price;

    private Integer capacity;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "voyage")
    private List<Ticket> ticketList;


}
