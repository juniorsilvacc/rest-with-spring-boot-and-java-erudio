package com.juniorsilvacc.erudio.dtos.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private Boolean authenticated;
	private Date created;
	private Date expirateion;
	private String accessToken;
	private String refreshToken;
	
	public TokenDTO() {
	}

	public TokenDTO(String userName, Boolean authenticated, Date created, Date expirateion, String accessToken,
			String refreshToken) {
		this.userName = userName;
		this.authenticated = authenticated;
		this.created = created;
		this.expirateion = expirateion;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Boolean getAuthenticated() {
		return authenticated;
	}
	
	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getExpirateion() {
		return expirateion;
	}
	
	public void setExpirateion(Date expirateion) {
		this.expirateion = expirateion;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken, authenticated, created, expirateion, refreshToken, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenDTO other = (TokenDTO) obj;
		return Objects.equals(accessToken, other.accessToken) && Objects.equals(authenticated, other.authenticated)
				&& Objects.equals(created, other.created) && Objects.equals(expirateion, other.expirateion)
				&& Objects.equals(refreshToken, other.refreshToken) && Objects.equals(userName, other.userName);
	}

}
