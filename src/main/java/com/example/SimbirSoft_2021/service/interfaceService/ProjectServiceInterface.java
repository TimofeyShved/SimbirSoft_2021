package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.Dto.ProjectDto;

public interface ProjectServiceInterface {
    public ProjectDto findByReleaseId(Long releaseId);
}
