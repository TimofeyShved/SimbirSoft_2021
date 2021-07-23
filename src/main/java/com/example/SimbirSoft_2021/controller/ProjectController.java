package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Управление проектами")
@RequestMapping("/control")
@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectCrud projectCRUD; // создаём интерфейс для взаимодействия с бд

    @Operation(summary = "Добавить проект")
    @PostMapping("/project") // создать
    public ResponseEntity registration(@RequestBody ProjectEntity projectEntity) throws Exception {
        try {
            projectService.registration(projectEntity);
            return ResponseEntity.ok(projectEntity);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список проектов")
    @GetMapping("/projects") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(projectCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранный проект")
    @GetMapping("/project/{projectId}") // взять
    public ResponseEntity getOne(@PathVariable Long projectId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.getOne(projectId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранный проект")
    @DeleteMapping("/project/{projectId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long projectId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.deleteOne(projectId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранного проекта")
    @PutMapping("/project/{projectId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long projectId, @RequestBody ProjectEntity projectEntity) throws Exception {
        try {
            return ResponseEntity.ok(projectService.updateOne(projectId, projectEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
