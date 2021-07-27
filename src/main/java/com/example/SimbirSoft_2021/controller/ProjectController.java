package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.entity.ProjectEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.ProjectCrud;
import com.example.SimbirSoft_2021.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Управление проектами")
@RequestMapping("/control")
@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Добавить проект")
    @RequestMapping(value = "/project", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody ProjectDto projectDto) throws Exception {
        try {
            return ResponseEntity.ok(projectService.registration(projectDto));
        }catch (ProjectExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ReleaseNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ProjectAndDateTimeExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить список проектов")
    @RequestMapping(value = "/projects", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(projectService.getAll());
        }catch (ProjectNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить выбранный проект")
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long projectId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.getOne(projectId));
        }catch (ProjectNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Удалить выбранный проект")
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.DELETE) // удалить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long projectId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.deleteOne(projectId));
        }catch (ProjectNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Обновить данные выбранного проекта")
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.PUT) // обновить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long projectId, @Validated @RequestBody ProjectDto projectDto) throws Exception {
        try {
            return ResponseEntity.ok(projectService.updateOne(projectId, projectDto));
        }catch (ProjectNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ProjectExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ReleaseNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ProjectAndDateTimeExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }
}
