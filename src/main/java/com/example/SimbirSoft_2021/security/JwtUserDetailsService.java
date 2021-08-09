package com.example.SimbirSoft_2021.security;

import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;
import com.example.SimbirSoft_2021.security.Jwt.JwtUser;
import com.example.SimbirSoft_2021.security.Jwt.JwtUserFactory;
import com.example.SimbirSoft_2021.service.UserService;
import com.example.SimbirSoft_2021.service.interfaceService.UserServiceInterface;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userService.findByEmail(email);
        if (user==null){
            throw new UsernameNotFoundException("User "+user.getEmail()+" Not Found Exception");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
