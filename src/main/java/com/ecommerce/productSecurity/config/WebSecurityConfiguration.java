package com.ecommerce.productSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

	@Autowired
	private JwtAuthenticationEntryPoint jwtEntryPoint;
	
	@Autowired
	private JwtRequestFilter filter;
	
	@Autowired
	private UserDetailsService jwtService;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
             http.csrf(csrf -> csrf.disable())
             .authenticationProvider(authenticationProvider())
             .exceptionHandling(exp -> exp.authenticationEntryPoint(jwtEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/authenticate", "/registerUser", "/createRole", "/product/getAll", "/product/{productId}").permitAll()
            .anyRequest()
            .authenticated());
            
            
            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
            
            return http.build();
    }
	
	@Bean
	public PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder builder)
	 * throws Exception {
	 * builder.userDetailsService(jwtService).passwordEncoder(pwdEncoder()); }
	 */
	
	@Bean
	  public DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(jwtService);
	      authProvider.setPasswordEncoder(pwdEncoder());
	   
	      return authProvider;
	  }
}
