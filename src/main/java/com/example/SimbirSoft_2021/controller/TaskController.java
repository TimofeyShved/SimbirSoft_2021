package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.TaskDto;
import com.example.SimbirSoft_2021.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 1 способ
//@RequiredArgsConstructor
@Tag(name = "Управление задачами")
@RequestMapping("/task")
@RestController
public class TaskController {

    // 2 способ
    //@Autowired
    //private TaskService taskService;

    private final TaskService taskService; // создаём интерфейс для взаимодействия с бд

    // 3 способ
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Добавить задачу")
    @RequestMapping(value = "/posttask", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody TaskDto taskDto) throws Exception {
        try {
            return ResponseEntity.ok(taskService.registration(taskDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список задач")
    @RequestMapping(value = "/gettasks", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(taskService.getAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список задач")
    @RequestMapping(value = "/gettasks", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(@Validated @RequestBody TaskDto taskDto){
        try {
            return ResponseEntity.ok(taskService.getAllCustom(taskDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранную задачу")
    @RequestMapping(value = "/gettask/{taskId}", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getOne(taskId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список задач по статусу")
    @RequestMapping(value = "/gettask/status", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOneByStatus(@Validated @RequestBody String status) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getAllByStatus(null, status));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список задач по статусу и проекту")
    @RequestMapping(value = "/gettask/status/{projectId}", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOneByStatusAndProject(@Validated @PathVariable Long projectId, @Validated @RequestBody String status) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getAllByStatus(projectId, status));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить количество задач по статусу")
    @RequestMapping(value = "/gettask/status/count", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCountByStatus(@Validated @RequestBody String status) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getCountByStatus(null, status));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить количество задач по статусу и проекту")
    @RequestMapping(value = "/gettask/status/count/{projectId}", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCountByStatusAndProject(@Validated @PathVariable Long projectId, @Validated @RequestBody String status) throws Exception {
        try {
            return ResponseEntity.ok(taskService.getCountByStatus(projectId, status));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранную задачу")
    @RequestMapping(value = "/deletetask/{taskId}", method = RequestMethod.DELETE) // удалить
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long taskId) throws Exception {
        try {
            return ResponseEntity.ok(taskService.deleteOne(taskId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранной задачи")
    @RequestMapping(value = "/puttask/{taskId}", method = RequestMethod.PUT) // обновить
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long taskId, @Validated @RequestBody TaskDto taskDto) throws Exception {
        try {
            return ResponseEntity.ok(taskService.updateOne(taskId, taskDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
