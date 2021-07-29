package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.entity.TaskEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.repository.TaskCrud;
import com.example.SimbirSoft_2021.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 1 способ
//@RequiredArgsConstructor
@Tag(name = "Управление задачами")
@RequestMapping("/control")
@RestController
public class TaskController {

    // 2 способ
    //@Autowired
    //private TaskService taskService;

    private TaskService taskService; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Добавить задачу")
    @RequestMapping(value = "/task", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody TaskDto taskDto) throws Exception {
        try {
            return ResponseEntity.ok(taskService.registration(taskDto));
        }catch (ReleaseExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ReleaseNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (TaskAndDateTimeExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить список задач")
    @RequestMapping(value = "/tasks", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(taskService.getAll());
        }catch (TaskNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить выбранную задачу")
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getOne(taskId));
        }catch (TaskNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Удалить выбранную задачу")
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE) // удалить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.deleteOne(taskId));
        }catch (TaskNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (BoardNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Обновить данные выбранной задачи")
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.PUT) // обновить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long taskId, @Validated @RequestBody TaskDto taskDto) throws Exception {
        try {
            return ResponseEntity.ok(taskService.updateOne(taskId, taskDto));
        }catch (TaskNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ReleaseExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ReleaseNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (TaskAndDateTimeExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }
}
