package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.BoardCrud;
import com.example.SimbirSoft_2021.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardCrud boardCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/board") // создать
    public ResponseEntity registration(@RequestBody BoardEntity boardEntity) throws Exception {
        try {
            boardService.registration(boardEntity);
            return ResponseEntity.ok(boardEntity);
        } catch (Exception e){
            return  ResponseEntity.badRequest().body("code: BOARD_EXISTS");
        }
    }

    @GetMapping("/boards") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(boardCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: BOARD_NOT_FOUND");
        }
    }

    @GetMapping("/board/{boardId}") // взять
    public ResponseEntity getOne(@PathVariable Long boardId) throws Exception {
        try {
            return ResponseEntity.ok(boardService.getOne(boardId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @DeleteMapping("/board/{boardId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long boardId) throws Exception {
        try {
            return ResponseEntity.ok(boardService.deleteOne(boardId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @PutMapping("/board/{boardId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long boardId, @RequestBody BoardEntity boardEntity) throws Exception {
        try {
            return ResponseEntity.ok(boardService.updateOne(boardId, boardEntity));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }
}
