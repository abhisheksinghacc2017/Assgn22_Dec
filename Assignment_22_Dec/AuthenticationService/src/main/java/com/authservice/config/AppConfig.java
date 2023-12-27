package com.authservice.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.authservice.entity.ApplicationUser;
import com.authservice.repository.ApplicationUserRepository;

@Configuration
public class AppConfig {

	@Autowired
	ApplicationUserRepository repo;

	@Bean
	public UserDetailsService userDetails() {

		UserDetailsService uds = username -> {

			Optional<ApplicationUser> appUser = repo.findById(username);
			if (appUser.isPresent()) {
				ApplicationUser aUser = appUser.get();

				return new User(aUser.getUsername(), aUser.getPassword(),
						Arrays.asList(new SimpleGrantedAuthority(aUser.getRole())));
			} else {
				throw new RuntimeException("User not found " + username);
			}

		};

		return uds;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder getEncoder() {

		return new BCryptPasswordEncoder();
	}

}
