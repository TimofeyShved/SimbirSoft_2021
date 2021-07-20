package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.BoardEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoardCRUD extends CrudRepository<BoardEntity, Long> { // наследуемый интерфейс для изменения данных в бд
// @Repository
//public interface BoardCRUD extends JpaRepository<BoardEntity, Long> {
    BoardEntity findBoardEntityByProjectId(Long projectId);
    BoardEntity findBoardEntityByTaskId(Long taskId);
}