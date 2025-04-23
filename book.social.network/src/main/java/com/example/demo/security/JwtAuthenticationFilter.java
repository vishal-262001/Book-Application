package com.example.demo.security;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private  JwtService jwtService;
	@Autowired
    private  UserDetailsService userDetailsService;


	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		if(request.getServletPath().contains("/api/v1/auth")){
			filterChain.doFilter(request, response);
			return;
		}
		
		final String authHeader =request.getHeader(AUTHORIZATION);
		final String jwt;
		final String userEmail;
		if(authHeader == null || !authHeader.startsWith("Bearer ")){
			filterChain.doFilter(request,response);
			return;
		}
		jwt =authHeader.substring(7);
		userEmail =jwtService.extractUserName(jwt);
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
			UserDetails userDetails =userDetailsService.loadUserByUsername(userEmail);
			if (jwtService.isTokenValid(jwt ,userDetails)){
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null ,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
	filterChain.doFilter(request,response);
	}

}
