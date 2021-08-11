package com.example.SimbirSoft_2021.security.Jwt;

import com.example.SimbirSoft_2021.Dto.RoleDto;
import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.RoleEntity;
import com.example.SimbirSoft_2021.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    // коснтруктор из обычного пользователя в нужного
    public static JwtUser create(UserEntity user){
        return new JwtUser(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoleEntities()))
        );
    }

    // переводим роли пользователя из прошлого вида в вид списка из слов
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleEntity> role){
        return role.stream()
                .map(x-> new SimpleGrantedAuthority(x.getRoleName())
                ).collect(Collectors.toList());
    }
}
