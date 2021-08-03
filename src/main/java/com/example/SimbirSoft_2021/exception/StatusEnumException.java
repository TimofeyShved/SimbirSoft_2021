package com.example.SimbirSoft_2021.exception;

public class StatusEnumException extends  Exception{
    private static final String message = "Ошибка (Error): Неправильно указан статус, нужно вот так BACKLOG, IN_PROGRESS, DONE, FAILURE (The status is incorrect, it should be like this BACKLOG, IN_PROGRESS, DONE, FAILURE)";
    public StatusEnumException(String message) {
        super(message);
    }
    public StatusEnumException() {
        super(message);
    }
}
