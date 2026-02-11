package com.api.utils;

import com.api.constants.Role;

public class DemoAuthTokenRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		while(true) {
		System.out.println(AuthTokenGenerator.getToken(Role.FD));
		}
	}

}
