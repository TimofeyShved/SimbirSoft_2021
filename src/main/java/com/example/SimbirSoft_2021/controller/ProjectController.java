package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.ProjectDto;
import com.example.SimbirSoft_2021.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 1 способ
//@RequiredArgsConstructor
@Tag(name = "Управление проектами")
@RequestMapping("/Project")
@RestController
public class ProjectController {

    // 2 способ
    //@Autowired
    //private final ProjectService projectService;

    private final ProjectService projectService;

    // 3 способ
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Добавить проект")
    @RequestMapping(value = "/postProject", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody ProjectDto projectDto) throws Exception {
        try {
            return ResponseEntity.ok(projectService.registration(projectDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список проектов")
    @RequestMapping(value = "/getProjects", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(projectService.getAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранный проект")
    @RequestMapping(value = "/getProject/{projectId}", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long projectId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.getOne(projectId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список проектов по статусу")
    @RequestMapping(value = "/getProject/status", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOneByStatus(@Validated @RequestBody String status) throws Exception {
        try {
            return ResponseEntity.ok(projectService.getAllByStatus(status));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Получить количество проектов по статусу")
    @RequestMapping(value = "/getProject/status/count", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCountByStatus(@Validated @RequestBody String status) throws Exception {
        try {
            return ResponseEntity.ok(projectService.getCountByStatus(status));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранный проект")
    @RequestMapping(value = "/deleteProject/{projectId}", method = RequestMethod.DELETE) // удалить
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long projectId) throws Exception {
        try {
            return ResponseEntity.ok(projectService.deleteOne(projectId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранного проекта")
    @RequestMapping(value = "/putProject/{projectId}", method = RequestMethod.PUT) // обновить
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long projectId, @Validated @RequestBody ProjectDto projectDto) throws Exception {
        try {
            return ResponseEntity.ok(projectService.updateOne(projectId, projectDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
