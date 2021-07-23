package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @Autowired
    private ReleaseCrud releaseCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/release") // создать
    public ResponseEntity registration(@RequestBody ReleaseEntity releaseEntity) throws Exception {
        try {
            releaseService.registration(releaseEntity);
            return ResponseEntity.ok(releaseEntity);
        } catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/releases") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(releaseCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/release/{releaseId}") // взять
    public ResponseEntity getOne(@PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.getOne(releaseId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/release/{releaseId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.deleteOne(releaseId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/release/{releaseId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long releaseId, @RequestBody ReleaseEntity releaseEntity) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.updateOne(releaseId, releaseEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
