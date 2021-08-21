package com.example.SimbirSoft_2021.security.Jwt;

import com.example.SimbirSoft_2021.entity.RoleEntity;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}") // секретное слово
    private String secret;

    @Value("${jwt.token.expired}") // время на обновление в (сек)
    private Long validityInMilliseconds;

    @Autowired
    private UserDetailsService userDetailsService;

    // что бы закодировать пароль нашего пользователя
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return  bCryptPasswordEncoder;
    }

    // инициализация, кодировка серетного слова
    @PostConstruct
    protected void init(){secret = Base64.getEncoder().encodeToString(secret.getBytes());}

    // создание токена
    public String createToken(String username, List<RoleEntity> roleEntityList){
        Claims claims = Jwts.claims().setSubject(username); // создаём климо по имени
        claims.put("roles", getRoleNames(roleEntityList)); // а так же записываем в него роли

        Date now = new Date(); // устанавливаем время
        Date vlidation = new Date(now.getTime()+validityInMilliseconds);

        return Jwts.builder() // собираем токен
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(vlidation)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication (String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // вернёт имя
    public String getUsername (String token){
        System.out.println("getUsername "+ Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    // проверка пришедшего токена
    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization"); // если увидими в заголовка это слово
        if(bearerToken != null && bearerToken.startsWith("Bearer_")){ // и в передаваемом наш токен
            System.out.println("Token Authorization == true");
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    // проверка токена
    public  boolean validateToken(String token){
        try{
            // получаем климо, распарсив токен через серетное слово
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())){ // если не проходит по времени
                System.out.println("Token не проходит по времени");
                return false;
            }
            System.out.println("Token проходит, validate Token");
            return  true;
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("Jwt token is expired or invalid");
        }
    }

    // на основании списка ролей пользователя, вернёт имена ролей
    private List<String> getRoleNames(List<RoleEntity> roleEntityList){
        List<String> result = new ArrayList<>(); // массив сток

        roleEntityList.forEach(x -> {
            System.out.println("getRoleNames: "+x.getRoleName());
            result.add(x.getRoleName()); // сохраняет роль
        });

        return result;
    }
}
