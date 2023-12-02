package com.example.BikeApplication.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//(securedEnabled = true,
//jsr250Enabled = true,
//prePostEnabled = true) // by default
public class WebSecurityConfig {

    private final CorsCustomizer corsCustomizer;

    public WebSecurityConfig(CorsCustomizer corsCustomizer) {
        this.corsCustomizer = corsCustomizer;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        corsCustomizer.corsCustomizer(httpSecurity);
        httpSecurity.authorizeHttpRequests().anyRequest().permitAll().and().csrf().disable();
        return httpSecurity.build();
    }
//    @Bean
//    public Validator validator() {
//        return Validation.buildDefaultValidatorFactory().getValidator();
//    }

}


