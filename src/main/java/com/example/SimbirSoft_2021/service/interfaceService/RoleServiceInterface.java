package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.Dto.RoleDto;
import com.example.SimbirSoft_2021.exception.RoleNotFoundException;

import java.util.List;

public interface RoleServiceInterface {
    public List<RoleDto> deleteByTaskId(Long taskId) throws Exception;
    public List<RoleDto> deleteByUserId(Long userId) throws Exception;
}
