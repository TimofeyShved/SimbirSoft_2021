package com.example.SimbirSoft_2021.service;

import com.example.SimbirSoft_2021.Dto.BoardDto;
import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.mappers.BoardMapper;
import com.example.SimbirSoft_2021.mappers.UserMapper;
import com.example.SimbirSoft_2021.repository.BoardCrud;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.interfaceService.BoardServiceInterface;
import com.example.SimbirSoft_2021.service.interfaceService.StandartServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

// 1 способ
//@RequiredArgsConstructor
@Service
public class BoardService implements StandartServiceInterface, BoardServiceInterface {

    // 2 способ
    //@Autowired
    //private BoardCrud boardCrud;

    private BoardCrud boardCrud; // создаём интерфейс для взаимодействия с бд
    private ProjectCrud projectCrud;
    private TaskCrud taskCrud;

    // 3 способ
    public BoardService(BoardCrud boardCrud, ProjectCrud projectCrud, TaskCrud taskCrud) {
        this.boardCrud = boardCrud;
        this.projectCrud = projectCrud;
        this.taskCrud = taskCrud;
    }

    @Transactional
    @Override
    public BoardDto registration(Object o) throws BoardExistsException, ProjectNotFoundException, TaskNotFoundException {
        BoardDto boardDto = (BoardDto) o;
        BoardEntity boardEntity = BoardMapper.INSTANCE.toEntity(boardDto);
        if (projectCrud.findByProjectId(boardDto.getProjectId())==null){
            throw new ProjectNotFoundException();
        }
        if (taskCrud.findByTaskId(boardDto.getTaskId())==null){
            throw new TaskNotFoundException();
        }
        if ((boardCrud.findByTaskId(boardEntity.getTaskId())!=null)){
            throw new BoardExistsException();
        }
        boardCrud.save(boardEntity);
        return BoardMapper.INSTANCE.toDto(boardEntity);
    }

    @Transactional
    @Override
    public List<BoardDto> getAll() throws BoardNotFoundException {
        List<BoardEntity> boardEntityList = boardCrud.findAll();
        if (boardEntityList==null){
            throw new BoardNotFoundException();
        }
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (BoardEntity e:boardEntityList){
            boardDtoList.add(BoardMapper.INSTANCE.toDto(e));
        }
        return boardDtoList;
    }

    @Transactional
    @Override
    public BoardDto getOne(Long id) throws BoardNotFoundException {
        BoardEntity boardEntity = boardCrud.findByBoardId(id);
        if (boardEntity==null){
            throw new BoardNotFoundException();
        }
        return BoardMapper.INSTANCE.toDto(boardEntity);
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
    public BoardDto updateOne(Long id, Object o) throws BoardNotFoundException, BoardExistsException, ProjectNotFoundException, TaskNotFoundException {
        if (boardCrud.findByBoardId(id)==null){
            throw new BoardNotFoundException();
        }
        BoardEntity boardEntityNew = BoardMapper.INSTANCE.toEntity((BoardDto) o);
        BoardEntity boardEntity = boardCrud.findByBoardId(id);

        if (projectCrud.findByProjectId(boardEntityNew.getProjectId())==null){
            throw new ProjectNotFoundException();
        }
        if (taskCrud.findByTaskId(boardEntityNew.getTaskId())==null){
            throw new TaskNotFoundException();
        }
        if (boardCrud.findByTaskId(boardEntityNew.getTaskId())!=null){
            throw new BoardExistsException();
        }

        boardEntity.setProjectId(boardEntityNew.getProjectId());
        boardEntity.setTaskId(boardEntityNew.getTaskId());
        boardCrud.save(boardEntity);
        return BoardMapper.INSTANCE.toDto(boardEntity);
    }
}
