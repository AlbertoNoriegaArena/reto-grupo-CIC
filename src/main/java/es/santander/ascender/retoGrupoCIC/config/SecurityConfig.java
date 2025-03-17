package es.santander.ascender.retoGrupoCIC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // Permite que la consola de H2 se cargue en un iframe si la petición proviene del mismo origen (localhost).
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF (solo para desarrollo)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permitir acceso a Swagger
                        .requestMatchers("/h2-console/**").permitAll() // Permitir acceso a H2
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()) // Todo lo demás requiere autenticación
                .formLogin(login -> login.disable()) // Deshabilita el formulario de login
                .httpBasic(httpBasic -> httpBasic.disable()); // Deshabilita autenticación básica

        return http.build();
    }
}
