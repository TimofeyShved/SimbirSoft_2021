package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.exception.ReleaseNotFoundException;
import com.example.SimbirSoft_2021.exception.RoleNotFoundException;
import com.example.SimbirSoft_2021.exception.TaskNotFoundException;

import java.util.List;

public interface TaskServiceInterface {
    public TaskDto findByReleaseId(Long releaseId) throws TaskNotFoundException;
    public List<TaskDto> deleteTaskByProjectId(Long projectId) throws TaskNotFoundException, ReleaseNotFoundException, RoleNotFoundException;
}
