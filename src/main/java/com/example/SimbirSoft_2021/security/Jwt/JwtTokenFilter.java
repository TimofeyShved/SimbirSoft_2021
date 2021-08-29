package com.example.SimbirSoft_2021.security.Jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// фильтрует на наличие токена
public class JwtTokenFilter extends GenericFilterBean {

    // нужен для проверки токена
    private JwtTokenProvider jwtTokenProvider;

    // конструктор
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // фильтрация
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // получаю токен через запрос
        System.out.println("получаю токен через запрос");
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

        // если он валиден
        if ((token != null) && jwtTokenProvider.validateToken(token)){
            System.out.println("если он валиден");
            // берём аунтефикацию из токена
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            if (authentication != null){ // то берем её из токена
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(" берём аунтефикацию из токена");
            }
        }
        // дальше на проверку
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
