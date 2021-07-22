package com.example.SimbirSoft_2021.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Schema(description = "Люди")
public class UserDto { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Schema(description = "Id человека")
    private Long userId;

    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Фамилия")
    private String lastName;

    @Schema(description = "Отчество")
    private String patronymic;

    public UserDto(){ // конструктор
    }

    public UserDto(String firstName, String lastName, String patronymic) {
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
