package com.api.request.model;

public record Usercredentials (

	String username,
	String password) {

	@Override
	public String toString() {
		return "Usercredentials [username=" + username + ", password=" +"******"+ "]";
	}

}
