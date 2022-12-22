package com.ldap.springbootusermanagementldap.jwt;

import com.ldap.springbootusermanagementldap.model.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	private static final long EXPIRE_DURATION = 60 * 60 * 1000; // 1 hour
	
	@Value("${app.jwt.secret}")
	private String SECRET_KEY;

	public String generateAccessToken(User user) {
		
		return Jwts.builder()
				.setSubject(String.format("%s", user.getUsername()))
				.setIssuer("Muho")
				.claim("roles", user.getRoles().stream().collect(Collectors.toList()))
				.setIssuedAt(new Date())
				.setExpiration(getCustomExpirationTime())
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException ex) {
			LOGGER.error("JWT expired", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
		} catch (MalformedJwtException ex) {
			LOGGER.error("JWT is invalid", ex);
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT is not supported", ex);
		} catch (SignatureException ex) {
			LOGGER.error("Signature validation failed");
		}
		
		return false;
	}
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	public Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}

	public Date getCustomExpirationTime() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, 10);
		return c.getTime();
	}
}
