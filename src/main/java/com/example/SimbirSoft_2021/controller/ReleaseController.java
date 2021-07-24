package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.exception.ProjectExistsException;
import com.example.SimbirSoft_2021.exception.ProjectNotFoundException;
import com.example.SimbirSoft_2021.exception.ReleaseExistsException;
import com.example.SimbirSoft_2021.exception.ReleaseNotFoundException;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.ReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Управление временем реализации")
@RequestMapping("/control")
@RestController
public class ReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @Operation(summary = "Добавить время реализации")
    @RequestMapping(value = "/release", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody ReleaseEntity releaseEntity) throws Exception {
        try {
            releaseService.registration(releaseEntity);
            return ResponseEntity.ok(releaseEntity);
        }catch (ReleaseExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить список времён реализации")
    @RequestMapping(value = "/releases", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(releaseService.getAll());
        }catch (ReleaseNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Получить выбранное время реализации")
    @RequestMapping(value = "/release/{releaseId}", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.getOne(releaseId));
        }catch (ReleaseNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Удалить выбранное время реализации")
    @RequestMapping(value = "/release/{releaseId}", method = RequestMethod.DELETE) // удалить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.deleteOne(releaseId));
        }catch (ReleaseNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @Operation(summary = "Обновить данные выбранного времени реализации")
    @RequestMapping(value = "/release/{releaseId}", method = RequestMethod.PUT) // обновить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long releaseId, @Validated @RequestBody ReleaseEntity releaseEntity) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.updateOne(releaseId, releaseEntity));
        }catch (ReleaseNotFoundException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (ReleaseExistsException e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }
}
