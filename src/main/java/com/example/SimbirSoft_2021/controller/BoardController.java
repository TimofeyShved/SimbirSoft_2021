package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.BoardEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.BoardCrud;
import com.example.SimbirSoft_2021.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Управление досками")
@RequestMapping("/control")
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardCrud boardCrud; // создаём интерфейс для взаимодействия с бд

    @Operation(summary = "Добавить доску")
    @PostMapping("/board") // создать
    public ResponseEntity registration(@RequestBody BoardEntity boardEntity) {
        try {
            boardService.registration(boardEntity);
            return ResponseEntity.ok(boardEntity);
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить список досок")
    @GetMapping("/boards") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(boardCrud.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранную доску")
    @GetMapping("/board/{boardId}") // взять
    public ResponseEntity getOne(@PathVariable Long boardId) throws Exception {
        try {
            return ResponseEntity.ok(boardService.getOne(boardId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранную доску")
    @DeleteMapping("/board/{boardId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long boardId) throws Exception {
        try {
            return ResponseEntity.ok(boardService.deleteOne(boardId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранной доски")
    @PutMapping("/board/{boardId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long boardId, @RequestBody BoardEntity boardEntity) throws Exception {
        try {
            return ResponseEntity.ok(boardService.updateOne(boardId, boardEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
