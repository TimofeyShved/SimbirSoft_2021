package com.example.SimbirSoft_2021.exception;

public class RoleNotFoundException extends  Exception{
    private static final String message = "Ошибка (Error): Роль не найдена (Role not found)";
    public RoleNotFoundException(String message) {
        super(message);
    }
    public RoleNotFoundException() {
        super(message);
    }
}
