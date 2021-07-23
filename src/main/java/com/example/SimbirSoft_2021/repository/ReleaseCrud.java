package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//public interface ReleaseCrud extends CrudRepository<ReleaseEntity, Long> { // наследуемый интерфейс для изменения данных в бд

@Repository
public interface ReleaseCrud extends JpaRepository<ReleaseEntity, Long> {
    ReleaseEntity findReleaseEntityByDataStart(String dataStart);
    ReleaseEntity findReleaseEntityByDataEnd(String dataEnd);
}