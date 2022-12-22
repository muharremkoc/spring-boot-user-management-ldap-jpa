package com.ldap.springbootusermanagementldap.dto;

public class AuthResponse {

	private String uid;
	private String accessToken;

	public AuthResponse() {
	}

	public AuthResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public AuthResponse(String uid, String accessToken) {
		this.uid = uid;
		this.accessToken = accessToken;

	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}


