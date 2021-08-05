package com.example.SimbirSoft_2021.exception;

public class UserNotFoundException extends  Exception{
    private static final String message = "Ошибка (Error): Пользователь не найден (User not found)";
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException() {
        super(message);
    }
}
