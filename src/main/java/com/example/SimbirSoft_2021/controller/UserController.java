package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.UserCrud;
import com.example.SimbirSoft_2021.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// так как порт 8080 был занят, я его поменял на http://localhost:8070/user/ ------- не знаю, как у вас там всё устроено ¯\_(ツ)_/¯
@Tag(name = "Управление людьми")
@RequestMapping("/control")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserCrud userCRUD; // создаём интерфейс для взаимодействия с бд

    @Operation(summary = "Добавить человека")
    @PostMapping("/user") // создать
    public ResponseEntity registration(@RequestBody UserEntity userEntity) throws Exception {
        try {
            userService.registration(userEntity);
            return ResponseEntity.ok(userEntity);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список людей")
    @GetMapping("/users") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(userService.getAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранного человека")
    @GetMapping("/user/{userId}") // взять
    public ResponseEntity getOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.getOne(userId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранного человека")
    @DeleteMapping("/user/{userId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.deleteOne(userId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранного человека")
    @PutMapping("/user/{userId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long userId, @RequestBody UserEntity userEntity) throws Exception {
        try {
            return ResponseEntity.ok(userService.updateOne(userId, userEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
