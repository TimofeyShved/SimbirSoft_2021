package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.UserCRUD;
import com.example.SimbirSoft_2021.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController             // так как порт 8080 был занят, я его поменял на http://localhost:8070/user/ ------- не знаю, как у вас там всё устроено ¯\_(ツ)_/¯
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCRUD userCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/user") // создать
    public ResponseEntity registration(@RequestBody UserEntity userEntity) throws Exception {
        System.out.println("------------");
        try {
            userService.registration(userEntity);
            return ResponseEntity.ok(userEntity);
        } catch (Exception e){
            return  ResponseEntity.badRequest().body("code: USER_EXISTS");
        }
    }

    @GetMapping("/users") // взять
    public ResponseEntity getUsers(){
        System.out.println("------------");
        try {
            return ResponseEntity.ok(userCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: USER_NOT_FOUND");
        }
    }

    @GetMapping("/user/{userId}") // взять
    public ResponseEntity getOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.getOne(userId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @DeleteMapping("/user/{userId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.deleteOne(userId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @PutMapping("/user/{userId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long userId, @RequestBody UserEntity user) throws Exception {
        try {
            return ResponseEntity.ok(userService.updateOne(userId, user));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }
}
