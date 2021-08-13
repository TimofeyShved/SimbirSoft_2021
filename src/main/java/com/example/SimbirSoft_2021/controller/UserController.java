package com.example.SimbirSoft_2021.controller;

import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.model.UserModel;
import com.example.SimbirSoft_2021.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


// так как порт 8080 был занят, я его поменял на http://localhost:8070/user/ ------- не знаю, как у вас там всё устроено ¯\_(ツ)_/¯
// 1 способ
//@RequiredArgsConstructor
@Tag(name = "Управление людьми")
@RequestMapping("/user")
@RestController
public class UserController {

    // 2 способ
    //@Autowired
    //private UserService userService;

    private final UserService userService;

    // 3 способ
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Добавить человека")
    @RequestMapping(value = "/postuser", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registration(@Validated @RequestBody UserDto userDto) throws Exception {
        try {
            return ResponseEntity.ok(userService.registration(userDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить список людей")
    @RequestMapping(value = "/getusers", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(){
        try {
            return ResponseEntity.ok(userService.getAll());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Получить выбранного человека")
    @RequestMapping(value = "/getuser/{userId}", method = RequestMethod.GET) // взять
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getOne(@Validated @PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.getOne(userId));
        }catch (Exception e){
            //return  ResponseEntity.badRequest().body(e.getMessage());
            return null;
        }
    }

    @Operation(summary = "Удалить выбранного человека")
    @RequestMapping(value = "/deleteuser/{userId}", method = RequestMethod.DELETE) // удалить
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOne(@Validated @PathVariable Long userId) throws Exception {
        try {
            return ResponseEntity.ok(userService.deleteOne(userId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Обновить данные выбранного человека")
    @RequestMapping(value = "/putuser/{userId}", method = RequestMethod.PUT) // обновить
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOne(@Validated @PathVariable Long userId, @Validated @RequestBody UserDto userDto) throws Exception {
        try {
            return ResponseEntity.ok(userService.updateOne(userId, userDto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
