package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.exception.ProjectNotFoundException;

import java.util.List;

public interface ProjectServiceInterface {
    public ProjectDto findByReleaseId(Long releaseId);
    public boolean deleteReleaseInProject(Long id);
    public List<ProjectDto> getAllByStatus(String status) throws ProjectNotFoundException;
    public Long getCountByStatus(String status) throws ProjectNotFoundException;
}
