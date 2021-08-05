package com.example.SimbirSoft_2021.enumertion;

import com.example.SimbirSoft_2021.exception.TaskNotFoundException;

public enum StatusEnum {
    BACKLOG, // закрыт
    IN_PROGRESS, // в процессе
    DONE, // выполнено
    FAILURE; // провалено
}
