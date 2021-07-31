package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProjectCRUD extends CrudRepository<ProjectEntity, Long> { // наследуемый интерфейс для изменения данных в бд
// @Repository
//public interface ProjectCRUD extends JpaRepository<ProjectEntity, Long> {
    ProjectEntity findProjectEntityByProjectName(String projectName);
}
