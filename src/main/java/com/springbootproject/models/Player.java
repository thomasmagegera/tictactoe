package com.springbootproject.models;

import org.springframework.stereotype.Component;

@Component
public class Player {
    private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
    
}
