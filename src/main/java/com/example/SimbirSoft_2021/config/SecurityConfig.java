package com.example.SimbirSoft_2021.config;

import com.example.SimbirSoft_2021.security.Jwt.JwtConfigurer;
import com.example.SimbirSoft_2021.security.Jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// конфигурация нашего доступа к нашему проекту
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // провайдер токена
    private final JwtTokenProvider jwtTokenProvider;

    // переменные пути
    private static final String USER = "/user/**";
    private static final String PROJECT = "/project/**";
    private static final String TASK = "/task/**";
    private static final String ROLE = "/role/**";
    private static final String DATATIMERELEASE = "/release/**";

    // author, customer, implementer

    // конструктор
    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // уантетификация
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // конфигурация запроса
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // защита от влома
                .httpBasic().disable()
                .csrf().disable()
                // отключаем сессию
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // должны быть авторезованы
                .authorizeRequests()
                // разрешить всем
                .antMatchers("/").permitAll()
                .antMatchers("/control/login").permitAll()
                // по ролям
                .antMatchers(""+TASK).hasAnyAuthority( "author", "implementer")
                .antMatchers(""+PROJECT).hasAnyAuthority("customer", "author", "implementer")
                .antMatchers(""+ROLE).hasAnyAuthority("customer", "author")
                .antMatchers(""+USER).hasAnyAuthority("customer", "author")
                .antMatchers(""+DATATIMERELEASE).hasAnyAuthority( "customer", "author")
                // все остальные запросы должны быть авторезованы
                .anyRequest().authenticated()
                .and()
                // проходя через провайдер токена
                .apply(new JwtConfigurer(jwtTokenProvider));

                /*
                .and()
                .formLogin()
                .loginPage("/control/login").permitAll()
                .defaultSuccessUrl("/").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/control/login", "POST"));

                 */
    }
}
