package com.ticket.Dto;

import com.ticket.Model.Enums.Gender;
import com.ticket.Model.Enums.Role;
import com.ticket.Model.Enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto {
    private String name;
    private String email;
    private String mobilePhone;
    private String password;
    private Role role;
    private UserType userType;
    private Gender gender;
}
