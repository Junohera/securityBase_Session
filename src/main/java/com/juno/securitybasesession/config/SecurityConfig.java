package com.juno.securitybasesession.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/sample/anonymouse").permitAll() // 모든 사용자 접근가능
                .antMatchers("/sample/member").hasRole("USER") // 유저권한만 접근가능
        ;

        http.formLogin(); // 인가/인증 문제시 로그인화면

        http.csrf().disable(); // REST의 경우 CSRF토큰 미발행

        http.logout(); // CSRF enable ? logout only POST : logout both POST, GET
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("$2a$10$q7TxjtT9/CmBboxEHG5jmudD3lN98gZsc0NLtGxB.t6WUeRTTNPMO") // encode(1111)
                .roles("USER");
    }
}
