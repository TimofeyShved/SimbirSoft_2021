package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.repository.RoleCrud;
import com.example.SimbirSoft_2021.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleCrud roleCRUD; // создаём интерфейс для взаимодействия с бд

    @PostMapping("/role") // создать
    public ResponseEntity registration(@RequestBody RoleEntity roleEntity) throws Exception {
        try {
            roleService.registration(roleEntity);
            return ResponseEntity.ok(roleEntity);
        } catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/rols") // взять
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(roleCRUD.findAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/role/{roleId}") // взять
    public ResponseEntity getOne(@PathVariable Long roleId) throws Exception {
        try {
            return ResponseEntity.ok(roleService.getOne(roleId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/role/{roleId}") // удалить
    public ResponseEntity deleteOne(@PathVariable Long roleId) throws Exception {
        try {
            return ResponseEntity.ok(roleService.deleteOne(roleId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/role/{roleId}") // обновить
    public ResponseEntity updateOne(@PathVariable Long roleId, @RequestBody RoleEntity roleEntity) throws Exception {
        try {
            return ResponseEntity.ok(roleService.updateOne(roleId, roleEntity));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
