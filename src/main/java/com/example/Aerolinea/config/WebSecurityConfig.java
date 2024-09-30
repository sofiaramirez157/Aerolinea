package com.example.Aerolinea.config;

import com.example.Aerolinea.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.disable())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/test/all").permitAll()
                                .requestMatchers("/api/test/user").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers("/api/test/admin").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/pet/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/adoption/applications/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/adoption/applications/delete/").hasAuthority("ADMIN")
                                .requestMatchers("/api/adoption/applications/update/").hasAuthority("ADMIN")
                                .requestMatchers("/api/adoption/applications/getAll").permitAll()
                                .requestMatchers("/api/v1/pet/delete/").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/pet/update/").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/pet/getAll").permitAll()
                                .requestMatchers("/api/v1/donations/**").hasAnyAuthority("ADMIN", "USER")
                                .requestMatchers("/api/v1/donations/delete/").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/donations/update/**").hasAuthority("ADMIN")
                                .requestMatchers("/api/v1/donations/getAll", "/api/v1/post/getAll").permitAll()
                                .anyRequest().authenticated()

                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}