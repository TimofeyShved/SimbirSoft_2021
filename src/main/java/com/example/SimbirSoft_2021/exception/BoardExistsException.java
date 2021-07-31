package com.example.SimbirSoft_2021.exception;

public class BoardExistsException extends  Exception{
    private static String message = "code: BOARD_EXISTS";
    public BoardExistsException(String message) {
        super(message);
    }
    public BoardExistsException() {
        super(message);
    }
}
