package com.generation.blogpessoal.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
			
	@Autowired
	private JwtService jwtService;
		
    @Autowired
	private UserDetailsServiceImpl userDetailsService;
		
	@Override
	// metodo de filtragem ( filtra a requisição (Request), analisa a resposta (respose) se estiver correto o autthHeader vai armazenar essa autorização ("Authorization")
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
			    
		try{
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
			    token = authHeader.substring(7);// 7 é pq ele está pegando do "Bearer " que contanto tem 6 caracteres 7 com o espaço por isso .
			    username = jwtService.extractUsername(token);
			 }
		// verifica se o usuário é vazio e preenche
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			                    
			    if (jwtService.validateToken(token, userDetails)) {
			        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			        SecurityContextHolder.getContext().setAuthentication(authToken);
			     }
			            
			}
			filterChain.doFilter(request, response);
		
	    }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException 
			    | SignatureException | ResponseStatusException e){
		    response.setStatus(HttpStatus.FORBIDDEN.value());
			return;
		 }
     }
		
 }
