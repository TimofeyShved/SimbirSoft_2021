package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.BoardCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardCRUD boardCRUD; // создаём интерфейс для взаимодействия с бд

    public BoardEntity registration(BoardEntity boardEntity) throws Exception {
        if ((boardCRUD.findBoardEntityByProjectId(boardEntity.getProjectId())!=null)&&(boardCRUD.findBoardEntityByProjectId(boardEntity.getTaskId())!=null)){
            throw new Exception("code: BOARD_EXISTS");
        }
        return boardCRUD.save(boardEntity);
    }

    public BoardEntity getOne(Long id) throws UserNotFoundException {
        BoardEntity boardEntity = boardCRUD.findById(id).get();
        if (boardEntity==null){
            throw new UserNotFoundException("code: BOARD_NOT_FOUND");
        }
        return boardEntity;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (boardCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: BOARD_NOT_FOUND");
        }
        boardCRUD.deleteById(id);
        return id;
    }

    public BoardEntity updateOne(Long id, BoardEntity boardEntityNew) throws Exception {
        BoardEntity boardEntity = boardCRUD.findById(id).get();
        if (boardCRUD.findById(id).get()==null){
            throw new UserNotFoundException("code: BOARD_NOT_FOUND");
        }
        if ((boardCRUD.findBoardEntityByProjectId(boardEntity.getProjectId())!=null)&&(boardCRUD.findBoardEntityByProjectId(boardEntity.getTaskId())!=null)){
            throw new Exception("code: BOARD_EXISTS");
        }
        boardEntity.setProjectId(boardEntityNew.getProjectId());
        boardEntity.setTaskId(boardEntityNew.getTaskId());
        return boardCRUD.save(boardEntity);
    }
}
