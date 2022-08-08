package com.ticket.Service;

import com.ticket.Client.PaymentClient;
import com.ticket.Dto.EmailDto;
import com.ticket.Dto.Enums.PaymentType;
import com.ticket.Dto.Payment;
import com.ticket.Dto.SmsDto;
import com.ticket.Dto.TicketCreateRequestDto;

import com.ticket.Exception.*;
import com.ticket.Model.Enums.Gender;
import com.ticket.Model.Enums.Role;
import com.ticket.Model.Ticket;
import com.ticket.Model.User;
import com.ticket.Model.Voyage;
import com.ticket.Repository.TicketRepository;
import com.ticket.Repository.UserRepository;
import com.ticket.Repository.VoyageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public Ticket createTicket(TicketCreateRequestDto ticketCreateRequestDto) {
        Voyage voyageById = voyageRepository.findById(ticketCreateRequestDto.getVoyageId())
                .orElseThrow(()-> new VoyageNotFoundException("sefer bulunamadı"));
        User userById = userRepository.findById(ticketCreateRequestDto.getUserId()).orElseThrow();
        Ticket ticket = new Ticket();
        ticket.setUser(userById);
        ticket.setVoyage(voyageById);
        if (userById.getRole().equals(Role.INDIVIDUAL)) {
            if (!checkIndividualsTicketNumberAndVoyageNumber(ticketCreateRequestDto.getUserId(), ticketCreateRequestDto.getVoyageId())) {
                throw new IndividualMustBeLessThanFiveException("bireysel bilet alımlarında 5'ten fazla olamaz");
            }
        }
        else if (userById.getRole().equals(Role.CORPORATE)) {
            if (!checkCorporatesTicketNumberAndVoyageNumber(ticketCreateRequestDto.getUserId(), ticketCreateRequestDto.getVoyageId())) {
                throw new CorporateMustBeLessThanTwentyException("kurumsal bilet alımlarında 20'den fazla olamaz");
            }

        }
        Payment payment=paymentClient.createPayment(new Payment(LocalDateTime.now(),ticket.getVoyage().getPrice(), PaymentType.BANKTRANSFER));
        rabbitTemplate.convertAndSend("${rabbitmq.queue}",
                new SmsDto(ticket.getUser().getMobilePhone(),
                        "biletiniz alınmıştır",
                        "hayırlı yolculuklar dileriz","Sms"));
        log.info("sms gönderilmiştir");
        return ticketRepository.save(ticket);
    }

    public List<Ticket> createTicket(List<TicketCreateRequestDto> ticketCreateRequestDtoList) {
        List<Ticket> ticketList = new ArrayList();
        for(int i = 0 ; i < ticketCreateRequestDtoList.size(); i++){
            Voyage voyage = voyageRepository.findById(ticketCreateRequestDtoList.get(i).getVoyageId()).orElseThrow();
            User user = userRepository.findById(ticketCreateRequestDtoList.get(i).getUserId()).orElseThrow();
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setVoyage(voyage);
            ticketList.add(ticket);
        }
        if(checkSaleTwoMan(ticketList)){
            throw new CantSaleTwoMan("iki erkekten fazla eklenemez.");
        }
        for (int i = 0; i < ticketList.size(); i++) {
            Payment payment=paymentClient.createPayment(new Payment(LocalDateTime.now(),ticketList.get(i).getVoyage().getPrice(), PaymentType.BANKTRANSFER));
            rabbitTemplate.convertAndSend("${rabbitmq.queue}",
                    new SmsDto(ticketList.get(i).getUser().getMobilePhone(),
                            "biletiniz alınmıştır",
                            "hayırlı yolculuklar dileriz","Sms"));
            log.info("sms gönderilmiştir");
        }

        return ticketRepository.saveAll(ticketList);
    }
    private Boolean checkSaleTwoMan(List<Ticket> ticketList) {
        int count=0;
        for (int i = 0; i <ticketList.size() ; i++) {
            if(ticketList.get(i).getUser().getGender().equals(Gender.MAN)){
                count++;
            }
        }
        return count > 2;
    }

    private Boolean checkIndividualsTicketNumberAndVoyageNumber(Integer userId, Integer voyageId) {
        List<Ticket> ticketList = ticketRepository.findTicketsByUserIdAndVoyageId(userId, voyageId);
        return ticketList.size() < 5;
    }

    private Boolean checkCorporatesTicketNumberAndVoyageNumber(Integer userId, Integer voyageId) {
        List<Ticket> ticketList = ticketRepository.findTicketsByUserIdAndVoyageId(userId, voyageId);
        return ticketList.size() < 20;
    }


    public Ticket getByTicketId(Integer ticketId) {

        return ticketRepository.findById(ticketId).orElseThrow(()->new TicketNotFoundException("bilet bulunamadı"));
    }

    /*public TicketResponseDto getTicketById(Integer id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        User user = userRepository.findById(ticket.getUser().getId()).orElseThrow();
        Voyage voyage = voyageRepository.findById(ticket.getVoyage().getId()).orElseThrow();
        return new TicketResponseDto(ticket.getId(),voyage,user);
    }*/


    //toplam bilet satışımız
    public Integer getTotalSaleNumber() {

        return ticketRepository.findAll().size();
    }


    //toplam bilet satış tutarımız
    public BigDecimal getTotalPrice() {
        List<Ticket> ticketList=ticketRepository.findAll();
        return ticketList.stream().map(t->t.getVoyage().getPrice()).reduce(BigDecimal::add).orElseThrow(()->new TotalPriceException("toplam fiyat bulunamadı"));
    }
}
