package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.ReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Управление временем реализации")
@RequestMapping("/control")
@RestController
public class ReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private ReleaseCrud releaseCRUD; // создаём интерфейс для взаимодействия с бд

    @Operation(summary = "Добавить время реализации")
    @PostMapping("/release") // создать
    public ResponseEntity registration(@RequestBody ReleaseEntity releaseEntity) throws Exception {
        try {
            releaseService.registration(releaseEntity);
            return ResponseEntity.ok(releaseEntity);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список времён реализации")
    @GetMapping("/releases") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(releaseCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранное время реализации")
    @GetMapping("/release/{releaseId}") // взять
    public ResponseEntity getOne(@PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.getOne(releaseId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранное время реализации")
    @DeleteMapping("/release/{releaseId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.deleteOne(releaseId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранного времени реализации")
    @PutMapping("/release/{releaseId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long releaseId, @RequestBody ReleaseEntity releaseEntity) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.updateOne(releaseId, releaseEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
