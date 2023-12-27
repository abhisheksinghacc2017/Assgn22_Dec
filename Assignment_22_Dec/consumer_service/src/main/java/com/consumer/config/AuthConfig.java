package com.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.consumer.filter.JwtRequestFilter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


//-- Swagger JWT Configuration ------
/**
 * In Swagger if we want to pass authorization header globally in all rest apis by default then 
 * 'security = {@SecurityRequirement..' annotation is used here, Else it has to be applied on those resources only.
 */
@OpenAPIDefinition(
	info = @Info(
			title = "Consumer Service", 
			version = "v3"),
			security = {@SecurityRequirement(name = "bearerAuth")}
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER
) 
//END Swagger configuration


@Configuration
public class AuthConfig {

	@Autowired
	JwtRequestFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
        .authorizeHttpRequests((authz) -> authz
            .requestMatchers( new AntPathRequestMatcher("/selected/**")).hasAnyRole("ADMIN", "CUSTOMER")
            //.requestMatchers( new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
            .requestMatchers( new AntPathRequestMatcher( "/app/health")).permitAll()
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
