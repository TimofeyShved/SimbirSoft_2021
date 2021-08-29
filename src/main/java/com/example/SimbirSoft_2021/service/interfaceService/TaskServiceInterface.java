package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.exception.ReleaseNotFoundException;
import com.example.SimbirSoft_2021.exception.RoleNotFoundException;
import com.example.SimbirSoft_2021.exception.TaskNotFoundException;

import java.util.List;

public interface TaskServiceInterface {
    public TaskDto findByReleaseId(Long releaseId) throws TaskNotFoundException;
    public boolean deleteTaskByProjectId(Long projectId) throws TaskNotFoundException, ReleaseNotFoundException, RoleNotFoundException;
    public boolean deleteReleaseInTask(Long id) throws TaskNotFoundException;
    public List<TaskDto> getAllByStatus(Long projectId, String status) throws TaskNotFoundException;
    public Long getCountByStatus(Long projectId, String status) throws TaskNotFoundException;
    public List<TaskDto> getAllCustom(TaskDto cusomtaskDto) throws TaskNotFoundException;
}
