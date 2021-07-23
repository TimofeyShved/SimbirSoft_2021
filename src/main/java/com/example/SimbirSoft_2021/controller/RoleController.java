package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.repository.RoleCrud;
import com.example.SimbirSoft_2021.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Управление ролями")
@RequestMapping("/control")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleCrud roleCRUD; // создаём интерфейс для взаимодействия с бд

    @Operation(summary = "Добавить роль")
    @PostMapping("/role") // создать
    public ResponseEntity registration(@RequestBody RoleEntity roleEntity) throws Exception {
        try {
            roleService.registration(roleEntity);
            return ResponseEntity.ok(roleEntity);
        } catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список всех ролей")
    @GetMapping("/rols") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(roleCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранную роль")
    @GetMapping("/role/{roleId}") // взять
    public ResponseEntity getOne(@PathVariable Long roleId) throws Exception {
        try {
            return ResponseEntity.ok(roleService.getOne(roleId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Удалить выбранную роль")
    @DeleteMapping("/role/{roleId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long roleId) throws Exception {
        try {
            return ResponseEntity.ok(roleService.deleteOne(roleId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранной роли")
    @PutMapping("/role/{roleId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long roleId, @RequestBody RoleEntity roleEntity) throws Exception {
        try {
            return ResponseEntity.ok(roleService.updateOne(roleId, roleEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
