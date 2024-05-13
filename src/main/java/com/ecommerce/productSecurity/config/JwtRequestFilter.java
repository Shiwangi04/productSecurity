package com.ecommerce.productSecurity.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.productSecurity.service.JwtService;
import com.ecommerce.productSecurity.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	public static String CURRENT_USER = "";
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private JwtService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader("Authorization");
		String jwtToken = null;
		String username = null;
		if (header != null && header.startsWith("Bearer ")) {
			jwtToken = header.substring(7);
			
			try {
				username = util.getUserNameFromToken(jwtToken);
				CURRENT_USER = username;
			} catch(IllegalArgumentException e) {
				System.out.println("Unable to get Jwt Token");
			} catch (ExpiredJwtException e) {
				System.out.println("Expired Jwt Token");
			}
		} else {
			System.out.println("Jwt token does not start with Bearer!!");
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userdetails = service.loadUserByUsername(username);
			
			if (util.validateToken(jwtToken, userdetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
