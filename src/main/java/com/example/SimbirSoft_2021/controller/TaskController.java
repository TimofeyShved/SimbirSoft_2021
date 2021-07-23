package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Управление задачами")
@RequestMapping("/control")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskCrud taskCRUD; // создаём интерфейс для взаимодействия с бд

    @Operation(summary = "Добавить задачу")
    @PostMapping("/task") // создать
    public ResponseEntity registration(@RequestBody TaskEntity taskEntity) throws Exception {
        try {
            taskService.registration(taskEntity);
            return ResponseEntity.ok(taskEntity);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список задач")
    @GetMapping("/tasks") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(taskCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранную задачу")
    @GetMapping("/task/{taskId}") // взять
    public ResponseEntity getOne(@PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getOne(taskId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранную задачу")
    @DeleteMapping("/task/{taskId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.deleteOne(taskId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранной задачи")
    @PutMapping("/task/{taskId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long taskId, @RequestBody TaskEntity taskEntity) throws Exception {
        try {
            return ResponseEntity.ok(taskService.updateOne(taskId, taskEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
