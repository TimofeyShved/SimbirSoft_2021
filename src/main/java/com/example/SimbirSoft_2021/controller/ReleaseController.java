package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.ReleaseDto;
import com.example.SimbirSoft_2021.entity.ReleaseEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.ReleaseCrud;
import com.example.SimbirSoft_2021.service.ReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 1 способ
//@RequiredArgsConstructor
@Tag(name = "Управление временем реализации")
@RequestMapping("/control")
@RestController
public class ReleaseController {

    // 2 способ
    //@Autowired
    //private ReleaseService releaseService;

    private final ReleaseService releaseService;

    // 3 способ
    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    @Operation(summary = "Добавить время реализации")
    @RequestMapping(value = "/release", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody ReleaseDto releaseDto) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.registration(releaseDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список времён реализации")
    @RequestMapping(value = "/releases", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(releaseService.getAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранное время реализации")
    @RequestMapping(value = "/release/{releaseId}", method = RequestMethod.GET) // взять
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.getOne(releaseId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранное время реализации")
    @RequestMapping(value = "/release/{releaseId}", method = RequestMethod.DELETE) // удалить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long releaseId) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.deleteOne(releaseId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранного времени реализации")
    @RequestMapping(value = "/release/{releaseId}", method = RequestMethod.PUT) // обновить
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long releaseId, @Validated @RequestBody ReleaseDto releaseDto) throws Exception {
        try {
            return ResponseEntity.ok(releaseService.updateOne(releaseId, releaseDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
