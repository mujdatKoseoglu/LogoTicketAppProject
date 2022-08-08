package com.ticket.Service;

import com.ticket.Dto.UserCreateRequestDto;
import com.ticket.Exception.UserNotFoundException;
import com.ticket.Model.Enums.Gender;
import com.ticket.Model.Enums.Role;
import com.ticket.Model.Enums.UserType;
import com.ticket.Model.User;
import com.ticket.Repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AmqpTemplate amqpTemplate;

    @Test
    @DisplayName("it should create user")
    void createUserTest() {

        UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto("ahmet", "mgmail", "05351001010", "123", Role.CORPORATE, UserType.ADMIN, Gender.MAN);
        User createUser = userService.createUser(userCreateRequestDto);
        createUser.setName("ahmet");
        verify(userRepository).save(Mockito.any());
        assertEquals(userCreateRequestDto.getName(), createUser.getName());

    }

    @Test
    void getByUserIdTest() {
        when(userRepository.findById(5)).thenReturn(Optional.of(User.builder().id(5).name("recep").build()));
        User user = userService.getByUserId(5);
        assertEquals("recep", user.getName());

    }

    @Test
    void whenNotFindGetById_ShouldThrowUserNotFoundExceptionTest() {
        when(userRepository.findById(5)).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> userService.getByUserId(5))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("kullanıcı bulunamadı");
    }

    @Test
    void updateUserTest(){

        UserCreateRequestDto newUser = new UserCreateRequestDto();
        newUser.setName("New Test Name");
        newUser.setPassword("123");

        User user = new User();
        user.setId(5);
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());

        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
        userService.updateUser(user.getId(), newUser);

        verify(userRepository).save(user);
        verify(userRepository).findById(user.getId());
    }

    @Test
    void deleteUserTest(){
        User user=User.builder().id(5).build();
        when(userRepository.findById(5)).thenReturn(Optional.of(user));
        userService.deleteUser(5);
        verify(userRepository,times(1)).delete(user);
    }


}
