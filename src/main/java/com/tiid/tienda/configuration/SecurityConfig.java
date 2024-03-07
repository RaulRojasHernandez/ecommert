package com.tiid.tienda.configuration;


import com.tiid.tienda.filter.JwtFilterAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilterAuth jwtFilterAuth;
    private final AuthenticationProvider authProvider;

    private static final String[] PUBLIC_ENDPOINTS = {"/auth/**", "/public/**",
            "/product/all", "/category/all", "/discount/all",
            "/product/find/**", "/category/find/**", "/discount/find/**",
            "/product/search/**", "/category/search/**", "/discount/search/**"

    };

    private static final String[] CREATE_PRODUCT_ENDPOINT = {"/product/create"};
    private static final String[] EDIT_PRODUCT_ENDPOINT = {"/product/edit/**"};
    private static final String[] DELETE_PRODUCT_ENDPOINT = {"/product/delete/**"};
    private static final String[] EDIT_INVENTORY_ENDPOINT = {"/inventory/**"};


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                                .requestMatchers(CREATE_PRODUCT_ENDPOINT).hasAnyAuthority("CREATE_PRODUCT", "ADMIN")
                                .requestMatchers(EDIT_PRODUCT_ENDPOINT).hasAnyAuthority("EDIT_PRODUCT", "ADMIN")
                                .requestMatchers(DELETE_PRODUCT_ENDPOINT).hasAnyAuthority("DELETE_PRODUCT", "ADMIN")
                                .requestMatchers(EDIT_INVENTORY_ENDPOINT).hasAnyAuthority("EDIT_INVENTORY", "ADMIN")
                                .anyRequest()
                                .authenticated()
                            )
                .sessionManagement( sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtFilterAuth, UsernamePasswordAuthenticationFilter.class)
                .build();

    }



}
