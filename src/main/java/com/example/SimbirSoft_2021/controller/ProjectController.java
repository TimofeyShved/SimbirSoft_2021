package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.repository.ProjectCRUD;
import com.example.SimbirSoft_2021.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectCRUD projectCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/project") // создать
    public ResponseEntity registration(@RequestBody ProjectEntity projectEntity) throws Exception {
        System.out.println("------------");
        try {
            projectService.registration(projectEntity);
            return ResponseEntity.ok(projectEntity);
        } catch (Exception e){
            return  ResponseEntity.badRequest().body("code: USER_EXISTS");
        }
    }

    @GetMapping("/projects") // взять
    public ResponseEntity getUsers(){
        System.out.println("------------");
        try {
            return ResponseEntity.ok(projectCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: USER_NOT_FOUND");
        }
    }

    @GetMapping("/project/{projectId}") // взять
    public ResponseEntity getOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.getOne(userId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @DeleteMapping("/project/{projectId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.deleteOne(userId));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }

    @PutMapping("/project/{projectId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long userId, @RequestBody ProjectEntity projectEntity) throws Exception {
        try {
            return ResponseEntity.ok(projectService.updateOne(userId, projectEntity));
        }catch (UserNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("code: ERROR");
        }
    }
}
