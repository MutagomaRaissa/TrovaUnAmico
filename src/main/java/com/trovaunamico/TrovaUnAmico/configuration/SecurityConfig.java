package com.trovaunamico.TrovaUnAmico.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers(
                                "/", "/index.html", "/category.html", "/pet-details.html", "/about.html",
                                "/application.html",
                                "/js/**", "/css/**", "/images/**",
                                "/login.html"
                        ).permitAll()


                        .requestMatchers("/api/applications/my", "/api/applications/me").authenticated()


                        .requestMatchers("/api/applications/**").permitAll()
                        .requestMatchers("/user").permitAll()


                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2

                        .defaultSuccessUrl("/myApplications.html", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index.html")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @RestController
    public static class UserController {
        @GetMapping("/user")
        public Object user(@AuthenticationPrincipal OAuth2User principal) {
            return principal != null ? principal.getAttributes() : null;
        }
    }
}
