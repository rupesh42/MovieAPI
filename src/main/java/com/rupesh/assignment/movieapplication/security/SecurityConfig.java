package com.rupesh.assignment.movieapplication.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${app.security.users[0].username}")
	private String user1Username;

	@Value("${app.security.users[0].password}")
	private String user1Password;

	@Value("${app.security.users[0].role}")
	private String user1Role;

	@Value("${app.security.users[1].username}")
	private String user2Username;

	@Value("${app.security.users[1].password}")
	private String user2Password;

	@Value("${app.security.users[1].role}")
	private String user2Role;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername(user1Username).password(passwordEncoder().encode(user1Password))
				.roles(user1Role).build());
		manager.createUser(User.withUsername(user2Username).password(passwordEncoder().encode(user2Password))
				.roles(user2Role).build());
		return manager;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(
						(requests) -> requests.requestMatchers("/movies/**").authenticated().anyRequest().permitAll())
				.httpBasic(withDefaults());
		return http.build();
	}
	
}
