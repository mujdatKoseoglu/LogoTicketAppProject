package com.ticket.Service;

import com.ticket.Dto.UserCreateRequestDto;
import com.ticket.Dto.VoyageCreateRequestDto;
import com.ticket.Exception.UserNotFoundException;
import com.ticket.Exception.VoyageNotFoundException;
import com.ticket.Model.Enums.*;
import com.ticket.Model.User;
import com.ticket.Model.Voyage;
import com.ticket.Repository.UserRepository;
import com.ticket.Repository.VoyageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VoyageServiceTest {
    @InjectMocks
    private VoyageService voyageService;

    @Mock
    private VoyageRepository voyageRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("it should create voyage")
    void createVoyageTest() {
       VoyageCreateRequestDto voyageCreateRequestDto=VoyageCreateRequestDto.builder().destinationCity(City.ADANA).targetCity(City.ANKARA).adminId(5).build();
       User user=User.builder().id(5).userType(UserType.ADMIN).build();
       when(voyageService.getUser(5)).thenReturn(user);

        Voyage voyage=Voyage.builder().destinationCity(voyageCreateRequestDto.getDestinationCity())
                .targetCity(voyageCreateRequestDto.getTargetCity())
                .user(User.builder().id(5).userType(UserType.ADMIN).build()).build();

        Voyage voyage1=voyageService.createVoyage(voyageCreateRequestDto);
        verify(voyageRepository).save(voyage1);
        assertEquals(UserType.ADMIN, user.getUserType());

    }

    @Test
    void getByVoyageIdTest() {
        Mockito.when(voyageRepository.findById(5)).thenReturn(Optional.of(Voyage.builder().id(5).destinationCity(City.ADANA).build()));
        Voyage voyage = voyageService.getByVoyageId(5);
        assertEquals(City.ADANA, voyage.getDestinationCity());

    }

    @Test
    void whenNotFindGetById_ShouldThrowVoyageNotFoundExceptionTest() {
        when(voyageRepository.findById(5)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> voyageService.getByVoyageId(5))
                .isInstanceOf(VoyageNotFoundException.class)
                .hasMessageContaining("sefer bulunamadı");
    }
    @Test
    void deleteVoyageTest(){
        Voyage voyage=Voyage.builder().id(5).user(User.builder().id(5).userType(UserType.ADMIN).build()).build();
        when(voyageRepository.findById(5)).thenReturn(Optional.of(voyage));
        voyageService.deleteVoyage(voyage.getId(),voyage.getUser());
        verify(voyageRepository,times(1)).delete(voyage);
        assertEquals(voyage.getUser().getUserType(), UserType.ADMIN);
    }

    @Test
    void updateVoyageTest(){
        VoyageCreateRequestDto voyageCreateRequestDto=VoyageCreateRequestDto.builder().destinationCity(City.ADANA).targetCity(City.ANKARA).adminId(5).build();
        User user=User.builder().id(5).userType(UserType.ADMIN).build();
        when(voyageService.getUser(5)).thenReturn(user);
        Voyage voyage=Voyage.builder().id(5).build();
        given(voyageRepository.findById(voyage.getId())).willReturn(Optional.of(voyage));
        voyage.setDestinationCity(voyageCreateRequestDto.getDestinationCity());
        voyage.setTargetCity(voyageCreateRequestDto.getTargetCity());
        voyageService.updateVoyage(voyage.getId(),voyageCreateRequestDto);
        verify(voyageRepository).save(voyage);
        verify(voyageRepository).findById(voyage.getId());
    }
}
