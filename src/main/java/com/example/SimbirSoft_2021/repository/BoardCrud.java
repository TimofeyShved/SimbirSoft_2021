package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//public interface BoardCrud extends CrudRepository<BoardEntity, Long> { // наследуемый интерфейс для изменения данных в бд
@Repository
public interface BoardCrud extends JpaRepository<BoardEntity, Long> {
    BoardEntity findBoardEntityByProjectId(Long projectId);
    BoardEntity findBoardEntityByTaskId(Long taskId);
}