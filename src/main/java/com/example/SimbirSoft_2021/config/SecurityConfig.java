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

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String USER = "/user/**";
    private static final String PROJECT = "/project/**";
    private static final String TASK = "/task/**";
    private static final String ROLE = "/role/**";
    private static final String DATATIMERELEASE = "/release/**";

    // author, customer, implementer

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/control/login").permitAll()
                .antMatchers(""+TASK).hasAnyRole( "author", "implementer")
                .antMatchers(""+PROJECT).hasAnyRole("customer", "author", "implementer")
                .antMatchers(""+ROLE).hasAnyRole("customer", "author")
                .antMatchers(""+USER).hasAnyRole("customer", "author")
                .antMatchers(""+DATATIMERELEASE).hasAnyRole( "customer", "author")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
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
