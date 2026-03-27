package com.banksecure.security.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import com.banksecure.security.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.security.config.Customizer;

@Configuration // Indique que cette classe est une configuration Spring
@EnableWebSecurity // Active la sécurité web de Spring Security
@EnableMethodSecurity // Permet d'utiliser des annotations de sécurité au niveau des méthodes (ex: @PreAuthorize)
@RequiredArgsConstructor
public class SecurityConfig {
    // private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean // Indique que cette méthode produit un bean géré par Spring
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/comptes/**").authenticated()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().denyAll())
                .httpBasic(Customizer.withDefaults());
            // .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12); // 12 rounds de salage pour un bon compromis entre sécurité et performance donc 2 puissance 12 itérations
    }
}
