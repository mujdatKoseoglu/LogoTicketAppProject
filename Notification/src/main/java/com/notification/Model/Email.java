package com.notification.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="emailtable")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="emailtable")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String to;

    private String title;

    private String text;

}
