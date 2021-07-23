package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//public interface TaskCrud extends CrudRepository<TaskEntity, Long> { // наследуемый интерфейс для изменения данных в бд

@Repository
public interface TaskCrud extends JpaRepository<TaskEntity, Long> {
    TaskEntity findTaskEntityByTaskName(String taskName);
    TaskEntity findTaskEntityByTaskStatus(String taskStatus);
    TaskEntity findTaskEntityByReleaseId(Long releaseId);
}