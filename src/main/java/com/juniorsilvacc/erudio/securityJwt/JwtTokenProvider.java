package com.juniorsilvacc.erudio.securityJwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;
import com.juniorsilvacc.erudio.dtos.security.TokenDTO;

import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";
	
	@Value("${security.jwt.token.expire-lenght:3600000}")
	private long validatyInMilliSeconds = 3600000;
	
	@Autowired
	private UserDetailsService userDatailsService;
	
	Algorithm algorithm = null;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}
	
	public TokenDTO	createAccessToken (String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + validatyInMilliSeconds);
		var accessToken = getAccessToken(username, roles, now, validity);
		var refreshToken = getAccessToken(username, roles, now);
		return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
	}

	private String getAccessToken(String username, List<String> roles, Date now) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		// TODO Auto-generated method stub
		return null;
	}
}
