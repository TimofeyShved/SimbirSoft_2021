package com.example.SimbirSoft_2021.exception;

public class UserExistsException extends  Exception{
    private static final String message = "Ошибка (Error): Пользователь существует (User exists)";
    public UserExistsException(String message) {
        super(message);
    }
    public UserExistsException() {
        super(message);
    }
}
