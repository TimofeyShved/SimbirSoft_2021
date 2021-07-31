package com.example.SimbirSoft_2021.exception;

public class BoardNotFoundException extends  Exception{
    private static String message = "code: BOARD_NOT_FOUND";
    public BoardNotFoundException(String message) {
        super(message);
    }
    public BoardNotFoundException() {
        super(message);
    }
}
