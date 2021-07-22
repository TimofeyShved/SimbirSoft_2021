package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.TaskCRUD;
import com.example.SimbirSoft_2021.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskCRUD taskCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/task") // создать
    public ResponseEntity registration(@RequestBody TaskEntity taskEntity) throws Exception {
        try {
            taskService.registration(taskEntity);
            return ResponseEntity.ok(taskEntity);
        } catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tasks") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(taskCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/task/{taskId}") // взять
    public ResponseEntity getOne(@PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getOne(taskId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/task/{taskId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.deleteOne(taskId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/task/{taskId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long taskId, @RequestBody TaskEntity taskEntity) throws Exception {
        try {
            return ResponseEntity.ok(taskService.updateOne(taskId, taskEntity));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
