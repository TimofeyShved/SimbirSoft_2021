package com.example.SimbirSoft_2021.rest;

import com.example.SimbirSoft_2021.Dto.AuthenticationRequestDto;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.security.Jwt.JwtTokenProvider;
import com.example.SimbirSoft_2021.service.UserService;
import com.example.SimbirSoft_2021.service.interfaceService.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/control")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST) // создать
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws UserNotFoundException {
        try {
            String username = authenticationRequestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationRequestDto.getPassword()));
            UserEntity user = userService.findByEmail(username);

            if (user==null){
                throw new UserNotFoundException();
            }

            String token = jwtTokenProvider.createToken(username, user.getRoleEntities());

            Map<Object, Object> respons = new HashMap<>();
            respons.put("username", username);
            respons.put("token", token);

            return ResponseEntity.ok(respons);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }
    }
}
