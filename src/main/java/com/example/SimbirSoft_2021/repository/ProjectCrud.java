package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCrud extends JpaRepository<ProjectEntity, Long> {
    ProjectEntity findByProjectName(String projectName);
    ProjectEntity findByProjectId(Long projectId);
    ProjectEntity findByReleaseId(Long releaseId);
}
