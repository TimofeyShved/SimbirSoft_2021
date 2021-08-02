package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.RoleDto;
import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.exception.*;
import com.example.SimbirSoft_2021.repository.RoleCrud;
import com.example.SimbirSoft_2021.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 1 способ
//@RequiredArgsConstructor
@Tag(name = "Управление ролями")
@RequestMapping("/control")
@RestController
public class RoleController {

    // 2 способ
    //@Autowired
    //private RoleService roleService;

    private final RoleService roleService;

    // 3 способ
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Добавить роль")
    @RequestMapping(value = "/role", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody RoleDto roleDto) throws Exception {
        try {
            return ResponseEntity.ok(roleService.registration(roleDto));
        } catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список всех ролей")
    @RequestMapping(value = "/rols", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(roleService.getAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранную роль")
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getOne(@Validated @PathVariable Long roleId) throws Exception {
        try {
            return ResponseEntity.ok(roleService.getOne(roleId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранную роль")
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE) // удалить
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long roleId) throws Exception {
        try {
            return ResponseEntity.ok(roleService.deleteOne(roleId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранной роли")
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.PUT) // обновить
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@PathVariable Long roleId, @Validated @RequestBody RoleDto roleDto) throws Exception {
        try {
            return ResponseEntity.ok(roleService.updateOne(roleId, roleDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
