package com.postgresql.utilisateurs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/spotify/**").permitAll() // Autoriser tout le monde à accéder aux endpoints Spotify
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/spotify/login") // Utiliser votre page de connexion personnalisée
                        .permitAll() // Permettre à tous d'accéder à la page de connexion
                )
                .logout(logout -> logout
                        .permitAll() // Permettre à tous de se déconnecter
                );

        return http.build();
    }
}
