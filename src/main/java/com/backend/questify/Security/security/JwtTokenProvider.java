package com.backend.questify.Security.security;

import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
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

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
}