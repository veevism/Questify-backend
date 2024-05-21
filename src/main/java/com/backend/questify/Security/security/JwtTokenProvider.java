package com.backend.questify.Security.security;

import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

	private String secretKey = "mySecretKey";

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, Role role) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("role", role);

		Date now = new Date();
		Date validity = new Date(now.getTime() + 3600000); // 1h

		return Jwts.builder()
				   .setClaims(claims)
				   .setIssuedAt(now)
				   .setExpiration(validity)
				   .signWith(SignatureAlgorithm.HS256, secretKey)
				   .compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new ResourceNotFoundException("Expired or invalid JWT token");
		}
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	@Autowired
	private UserDetailsService userDetailsService;

	public UserDetails loadUserByUsername(String username) {
		return userDetailsService.loadUserByUsername(username);
	}
}