package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.BoardDto;
import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.BoardCrud;
import com.example.SimbirSoft_2021.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 1 способ
//@RequiredArgsConstructor
@Tag(name = "Управление досками")
@RequestMapping("/control")
@RestController
public class BoardController {

    // 2 способ
    //@Autowired
    //private BoardService boardService;

    private BoardService boardService;

    // 3 способ
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @Operation(summary = "Добавить доску")
    @RequestMapping(value = "/board", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody BoardDto boardDto) {
        try {
            return ResponseEntity.ok(boardService.registration(boardDto));
        }catch (BoardExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ProjectNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (TaskNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить список досок")
    @RequestMapping(value = "/boards", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(boardService.getAll());
        }catch (BoardNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить выбранную доску")
    @RequestMapping(value = "/board/{boardId}", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long boardId) throws Exception {
        try {
            return ResponseEntity.ok(boardService.getOne(boardId));
        }catch (BoardNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Удалить выбранную доску")
    @RequestMapping(value = "/board/{boardId}", method = RequestMethod.DELETE) // удалить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long boardId) throws Exception {
        try {
            return ResponseEntity.ok(boardService.deleteOne(boardId));
        }catch (BoardNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Обновить данные выбранной доски")
    @RequestMapping(value = "/board/{boardId}", method = RequestMethod.PUT) // обновить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long boardId, @Validated @RequestBody BoardDto boardDto) throws Exception {
        try {
            return ResponseEntity.ok(boardService.updateOne(boardId, boardDto));
        }catch (BoardNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (BoardExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ProjectNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (TaskNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
