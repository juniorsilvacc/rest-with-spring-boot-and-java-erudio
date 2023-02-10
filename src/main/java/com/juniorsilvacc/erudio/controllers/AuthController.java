package com.juniorsilvacc.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juniorsilvacc.erudio.dtos.security.AccountCredentialsDTO;
import com.juniorsilvacc.erudio.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Authentication Endpoint")
public class AuthController {
	
	@Autowired
	private AuthService service;
	
	@Operation(summary = "Authenticates a user and returns a token")
	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO data) {
		if(checkIfParamsIsNotNull(data)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		}
		
		var token = service.signin(data);
		
		if(token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		} else {
			return token;
		}
	}
	
	@Operation(summary = "Refresh token for Authenticated user and returns a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity<?> refreshToken(@PathVariable("username") String username,
			@RequestHeader("Authorization") String refreshToken) {
		if(checkIfParamsIsNotNull(username, refreshToken)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		}
		
		var token = service.refreshToken(username, refreshToken);
		
		if(token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
		} else {
			return token;
		}
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsDTO data) {
		return data == null || data.getUserName() == null || data.getUserName().isBlank()
				|| data.getPassword() == null || data.getPassword().isBlank();
	}

}
