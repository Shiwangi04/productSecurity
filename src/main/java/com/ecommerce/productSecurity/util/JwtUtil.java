package com.ecommerce.productSecurity.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final Integer TOKEN_VALIDITY = 3600 * 5;
	private final String SECRET_KEY  = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";
	
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}
	
	public boolean validateToken(String token, UserDetails user) {
		String username = getUserNameFromToken(token);
		return ( username.equals(user.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	private Key getSignInKey() {
	    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
	    return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(UserDetails userdetails) {
		Map<String , Object> claims = new HashMap<>();
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userdetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS256, getSignInKey())
				.compact();
	}
}
