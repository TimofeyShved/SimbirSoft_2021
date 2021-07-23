package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.BoardCrud;
import com.example.SimbirSoft_2021.service.interfaceService.BoardServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService implements StandartServiceInterface, BoardServiceInterface {

    @Autowired
    private BoardCrud boardCrud; // создаём интерфейс для взаимодействия с бд

    public BoardEntity registration(Object o) throws UserNotFoundException {
        BoardEntity boardEntity = (BoardEntity)o;
        if ((boardCrud.findByProjectIdAndTaskId((boardEntity.getProjectId()), boardEntity.getTaskId())!=null)){
            throw new UserNotFoundException("code: BOARD_EXISTS");
        }
        return boardCrud.save(boardEntity);
    }

    public BoardEntity getOne(Long id) throws UserNotFoundException {
        BoardEntity boardEntity = boardCrud.findById(id).get();
        if (boardEntity==null){
            throw new UserNotFoundException("code: BOARD_NOT_FOUND");
        }
        return boardEntity;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (boardCrud.findById(id).get()==null){
            throw new UserNotFoundException("code: BOARD_NOT_FOUND");
        }
        boardCrud.deleteById(id);
        return id;
    }

    public BoardEntity updateOne(Long id, Object o) throws Exception {
        BoardEntity boardEntityNew = (BoardEntity)o;
        BoardEntity boardEntity = boardCrud.findById(id).get();
        if (boardCrud.findById(id).get()==null){
            throw new UserNotFoundException("code: BOARD_NOT_FOUND");
        }
        if ((boardCrud.findByProjectIdAndTaskId((boardEntityNew.getProjectId()), boardEntityNew.getTaskId())!=null)){
            throw new UserNotFoundException("code: BOARD_EXISTS");
        }
        boardEntity.setProjectId(boardEntityNew.getProjectId());
        boardEntity.setTaskId(boardEntityNew.getTaskId());
        return boardCrud.save(boardEntity);
    }
}
