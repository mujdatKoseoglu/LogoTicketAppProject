package com.ticket.Service;

import com.ticket.Dto.VoyageCreateRequestDto;
import com.ticket.Exception.VoyageNotFoundException;
import com.ticket.Model.Enums.UserType;
import com.ticket.Model.User;
import com.ticket.Model.Voyage;
import com.ticket.Repository.UserRepository;
import com.ticket.Repository.VoyageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VoyageService {

    private final VoyageRepository voyageRepository;

    private final UserRepository userRepository;

    public VoyageService(VoyageRepository voyageRepository, UserRepository userRepository) {
        this.voyageRepository = voyageRepository;
        this.userRepository = userRepository;
    }

    //sefer ekleme
    public Voyage createVoyage(VoyageCreateRequestDto voyageCreateRequestDto) {
        Integer userId = voyageCreateRequestDto.getAdminId();
        User user = getUser(userId);
        Voyage voyage=new Voyage();
        voyage.setDestinationCity(voyageCreateRequestDto.getDestinationCity());
        voyage.setTargetCity(voyageCreateRequestDto.getTargetCity());
        voyage.setPrice(voyageCreateRequestDto.getPrice());
        voyage.setVehicle(voyageCreateRequestDto.getVehicle());
        voyage.setLocalDateTime(LocalDateTime.now());
        voyage.setCapacity(voyageCreateRequestDto.getCapacity());
        voyage.setUser(user);
        if (user.getUserType() == UserType.ADMIN) {
            voyageRepository.save(voyage);
            log.info("seferiniz eklenmiştir.");
        } else {
            log.info("admin değilseniz sefer ekleyemezsiniz");
        }
        return voyage;
    }

    @Async
    public User getUser(Integer userId) {
        return userRepository.getById(userId);
    }

    public Voyage getByVoyageId(Integer voyageId) {
        Voyage voyage1 = voyageRepository.findById(voyageId).orElseThrow(()->new VoyageNotFoundException("sefer bulunamadı"));
        return voyage1;
    }

    //sefer güncelleme
    public Voyage updateVoyage(Integer voyageId, VoyageCreateRequestDto voyageCreateRequestDto) {
        Integer userId = voyageCreateRequestDto.getAdminId();
        User user = getUser(userId);
        Voyage voyage1;
        if (user.getUserType() == UserType.ADMIN) {
            voyage1 = voyageRepository.findById(voyageId).orElseThrow(()-> new VoyageNotFoundException("sefer bulunamadı"));
            voyage1.setDestinationCity(voyageCreateRequestDto.getDestinationCity());
            voyage1.setTargetCity(voyageCreateRequestDto.getTargetCity());
            voyageRepository.save(voyage1);
            log.info("seferiniz yeniden güncellenmiştir");
            return voyage1;
        } else {
            log.info("admin olmadığınızdan seferiniz güncellenememiştir.");
        }
        return null;
    }

    //sefer iptal etme
    public Voyage deleteVoyage(Integer voyageId, User userRequest) {
        Voyage voyage1 = voyageRepository.findById(voyageId).get();
        if (userRequest.getUserType() == UserType.ADMIN) {
            voyageRepository.delete(voyage1);
            log.info("seferiniz iptal edilmiştir");
        } else {
            log.info("admin olmadığınızdan sefer iptal etme yetkiniz bulunmamaktadır");
        }
        return voyage1;
    }
    //varış yeri, kalkış yeri,tarih ve otobüs yada uçak seçimi yaptıktan sonra arama işlemini gerçekleştiriyoruz
    public List<Voyage> getByVoyageSearch(VoyageCreateRequestDto voyageCreateRequestDto) {
    List<Voyage> voyageList=getVoyageList();
    List<Voyage> voyageList1=voyageList.stream().filter(v->
                    v.getVehicle().equals(voyageCreateRequestDto.getVehicle()) &&//araç türü
                    v.getDestinationCity().equals(voyageCreateRequestDto.getDestinationCity()) &&//kalkış
                    v.getTargetCity().equals(voyageCreateRequestDto.getTargetCity())//varış
                    //&&v.getLocalDateTime().equals(voyageCreateRequestDto.getLocalDateTime())//tarih
                    ).collect(Collectors.toList());

        log.info("arama kriterlerinize göre uygun seferler gösterilmektedir");
        return voyageList1;
    }
    @Async
    public List<Voyage> getVoyageList() {
        return voyageRepository.findAll();
    }
}
