package com.example.SimbirSoft_2021.model;

import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserModel {

    // ----------------------------------------------- переменные
    private Long userId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;

    public UserModel(){ // конструктор
    }

    public UserModel(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.patronymic = userEntity.getPatronymic();
        this.email = userEntity.getEmail();
    }

    public UserModel(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.patronymic = userDto.getPatronymic();
        this.email = userDto.getEmail();
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
