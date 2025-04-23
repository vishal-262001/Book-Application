package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	@Autowired
	 private  JwtAuthenticationFilter jwtAuthenticationFilter;
     
	@Autowired
	private  AuthenticationProvider authenticationProvider;
     
     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .cors(withDefaults())
                 .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(req ->
                         req.requestMatchers(
                                         "/swagger-ui/**","/auth/**",
                                         "/v2/api-docs",
                                         "/v3/api-docs",
                                         "/v3/api-docs/**",
                                         "/swagger-resources",
                                         "/swagger-resources/**",
                                         "/configuration/ui",
                                         "/configuration/security",
                                         "/swagger-ui/**",
                                         "/webjars/**",
                                         "/swagger-ui.html"
                                 )
                                     .permitAll()
                                 .anyRequest()
                                     .authenticated()
                 )

                 .sessionManagement(session -> session
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session, using JWT
                     )
                     .authenticationProvider(authenticationProvider)
                     .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter


         return http.build();
     }
     
}
