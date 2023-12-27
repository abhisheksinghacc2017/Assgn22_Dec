package com.authservice.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.authservice.entity.ApplicationUser;
import com.authservice.filter.JwtRequestFilter;
import com.authservice.repository.ApplicationUserRepository;

@Configuration
public class AuthConfig {

	@Autowired
	JwtRequestFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		
		http
        .authorizeHttpRequests((authz) -> authz
            .requestMatchers( new AntPathRequestMatcher("/auth/home")).hasAnyRole("ADMIN", "CUSTOMER")
            .requestMatchers( new AntPathRequestMatcher( "/user/**")).permitAll()
            .requestMatchers( new AntPathRequestMatcher("/auth/login")).permitAll()
            .requestMatchers( new AntPathRequestMatcher( "/swagger-ui/**")).permitAll()
            .requestMatchers( new AntPathRequestMatcher( "/v3/api-docs/**")).permitAll()
            .anyRequest()
            .authenticated()
            )
		.sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.csrf(AbstractHttpConfigurer::disable);  
		
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
        
		return http.build();

	}

}
