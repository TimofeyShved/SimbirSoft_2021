package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReleaseCRUD extends CrudRepository<ReleaseEntity, Long> { // наследуемый интерфейс для изменения данных в бд
    // @Repository
//public interface ReleaseCRUD extends JpaRepository<TaskEntity, Long> {
    ReleaseEntity findReleaseEntityByDataStart(String dataStart);
    ReleaseEntity findReleaseEntityByDataEnd(String dataEnd);
}