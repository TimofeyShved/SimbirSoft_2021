package com.example.SimbirSoft_2021.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user_entity")
public class UserEntity { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<RoleEntity> roleEntities;

    public UserEntity(){ // конструктор
    }

    public UserEntity(String firstName, String lastName, String patronymic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    // ----------------------------------------------- гетеры и сетеры


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
