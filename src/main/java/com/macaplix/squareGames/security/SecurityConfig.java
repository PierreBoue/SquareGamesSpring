package com.macaplix.squareGames.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
//import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final MyUserDetailsService userDetailsService;
    //@Autowired
    //private AuthenticationManager authenticationManager;
    public SecurityConfig(final MyUserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        final AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        final var authenticationManager = authenticationManagerBuilder.build();
        http.authenticationManager(authenticationManager);
// Activer CORS et désactiver CSRF
        http = http.cors().and().csrf().disable();
// Modifier le manager de session pour utiliser le mode STATELESS
        http = http

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

// Renvoyer un code d’erreur en cas d’accès non autorisé
        http = http

                .exceptionHandling()
                .authenticationEntryPoint(

                        (request, response, ex) -> {
                            response.sendError(

                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()

                            );
                        }

                )
                .and();

                // Définir les autorisations d’accès aux ressources
                http.authorizeHttpRequests()

            // Les accès sans autorisation


            .requestMatchers("/**").permitAll()
            // Les autres accès
            .anyRequest().authenticated();        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*
    @Bean
    public AuthenticationManager authenticationManager() {
        return authenticationManager.getAuthenticationManager();
    }
*/
}
