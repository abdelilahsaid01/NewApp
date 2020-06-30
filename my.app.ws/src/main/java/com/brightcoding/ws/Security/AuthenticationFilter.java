package com.brightcoding.ws.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.brightcoding.ws.SpringApplicationContext;
import com.brightcoding.ws.Request.UserLoginRequest;
import com.brightcoding.ws.Services.UserService;
import com.brightcoding.ws.Shared.Dato.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			UserLoginRequest creds = new ObjectMapper().readValue(req.getInputStream(), UserLoginRequest.class); //Réception des données via Request http

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>())); // Cherche s'il existe dans la base de donnée

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	 @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {
	        
	        String userName = ((User) auth.getPrincipal()).getUsername(); 
	        
	        UserService userService=(UserService) SpringApplicationContext.getBean("userServiceImpl");	//nom de la classe en miniscule
	        UserDto userDto=userService.getUser(userName);	//récuperer l'émail
	        
	        String token = Jwts.builder()	//Information du tocken encodée. //Décodage sur jwt
	                .setSubject(userName)
	                .claim("id", userDto.getUserId())
	                .claim("name", userDto.getFirstName()+" "+userDto.getLastName())	//Envoter le tocken avec ces informations
	                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
	                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET )	//HEADER:ALGORITHM & TOKEN TYPE
	                .compact();	//pour décoder le tocken encodé : https://jwt.io/
	        
	        
	        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token); //s'affiche dans le header aprés authentification
	        res.addHeader("user_id",userDto.getUserId());
	        
	        res.getWriter().write("{\"token\":\""+token + "\", \"id\":\""+userDto.getUserId()+"\"}");	//Afficher le tocker et Id dans le body de response
	    	 }  
}
