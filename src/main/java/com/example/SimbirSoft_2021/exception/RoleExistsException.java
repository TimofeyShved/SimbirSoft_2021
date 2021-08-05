package com.example.SimbirSoft_2021.exception;

public class RoleExistsException extends  Exception{
    private static final String message = "Ошибка (Error): Такая роль уже существует (Role exists)";
    public RoleExistsException(String message) {
        super(message);
    }
    public RoleExistsException() {
        super(message);
    }
}
