package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.BoardDto;
import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.BoardExistsException;
import com.example.SimbirSoft_2021.exception.BoardNotFoundException;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.BoardCrud;
import com.example.SimbirSoft_2021.service.interfaceService.BoardServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class BoardService implements StandartServiceInterface, BoardServiceInterface {

    // 2 способ
    //@Autowired
    //private BoardCrud boardCrud;

    private BoardCrud boardCrud; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public BoardService(BoardCrud boardCrud) {
        this.boardCrud = boardCrud;
    }

    @Transactional
    @Override
    public BoardEntity registration(Object o) throws BoardExistsException {
        BoardEntity boardEntity = (BoardEntity)o;
        if ((boardCrud.findByProjectIdAndTaskId((boardEntity.getProjectId()), boardEntity.getTaskId())!=null)){
            throw new BoardExistsException();
        }
        return boardCrud.save(boardEntity);
    }

    @Transactional
    @Override
    public List<BoardEntity> getAll() throws BoardNotFoundException {
        List<BoardEntity> list = boardCrud.findAll();
        if (list==null){
            throw new BoardNotFoundException();
        }
        return list;
    }

    @Transactional
    @Override
    public BoardEntity getOne(Long id) throws BoardNotFoundException {
        BoardEntity boardEntity = boardCrud.findByBoardId(id);
        if (boardEntity==null){
            throw new BoardNotFoundException();
        }
        return boardEntity;
    }

    @Transactional
    @Override
    public Long deleteOne(Long id) throws BoardNotFoundException {
        if (boardCrud.findByBoardId(id)==null){
            throw new BoardNotFoundException();
        }
        boardCrud.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public BoardEntity updateOne(Long id, Object o) throws BoardNotFoundException, BoardExistsException {
        BoardEntity boardEntityNew = (BoardEntity)o;

        if (boardCrud.findByBoardId(id)==null){
            throw new BoardNotFoundException();
        }
        BoardEntity boardEntity = boardCrud.findByBoardId(id);

        if ((boardCrud.findByProjectIdAndTaskId((boardEntityNew.getProjectId()), boardEntityNew.getTaskId())!=null)){
            throw new BoardExistsException();
        }

        boardEntity.setProjectId(boardEntityNew.getProjectId());
        boardEntity.setTaskId(boardEntityNew.getTaskId());
        return boardCrud.save(boardEntity);
    }
}
