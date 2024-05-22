package com.backend.questify.Security.security;

import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

	private static final String SECRET_KEY = "myVerySecureSecretKeyThatShouldBeLongEnoughToMeetSecurityStandards123456";

	private Key secretKey;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // Generates a secure key
	}

	public String createToken(Long userId, Role role) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
		claims.put("role", role.name());

		Date now = new Date();
		Date validity = new Date(now.getTime() + 3600000); // 1 hour

		return Jwts.builder()
				   .setClaims(claims)
				   .setIssuedAt(now)
				   .setExpiration(validity)
				   .signWith(secretKey, SignatureAlgorithm.HS256)
				   .compact();
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getUserId(token).toString());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public Long getUserId(String token) {
		return Long.valueOf(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject());
	}

	public Map<String, Object> getClaims(String token) {
		Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
		return claimsJws.getBody();
	}
}