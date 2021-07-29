package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.Dto.BoardDto;
import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.exception.BoardNotFoundException;
import com.example.SimbirSoft_2021.exception.TaskNotFoundException;

import java.util.List;

public interface BoardServiceInterface {
    public void deleteByParammId(Long projectId, Long taskId) throws BoardNotFoundException, TaskNotFoundException;
}
