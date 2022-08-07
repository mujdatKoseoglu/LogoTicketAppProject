package com.ticket.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ticket.Model.Enums.Gender;
import com.ticket.Model.Enums.Role;
import com.ticket.Model.Enums.UserType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @NotBlank
    @Column(unique = true)
    @Email
    private String email;

    private String mobilePhone;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Voyage> voyageList;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Ticket> ticketList;

}
