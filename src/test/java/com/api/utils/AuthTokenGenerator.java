package com.api.utils;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import static com.api.constants.Role.*;

import com.api.constants.Role;
import com.api.pojo.Usercredentials;

import io.restassured.http.ContentType;

public class AuthTokenGenerator {
	
	private AuthTokenGenerator() {
		
	}
	
	public static String getToken(Role role) {
		Usercredentials credentials = null;
		if (role == FD) {
			credentials = new Usercredentials("iamfd" , "password");
		} else if (role == SUP) {
			credentials = new Usercredentials("iamsup" , "password");
		} else if (role == ENG) {
			credentials = new Usercredentials("iameng" , "password");
		} else if (role == QC) {
			credentials = new Usercredentials("iamqc" , "password");
		}
		String token = null;
		try {
			token = given().baseUri(ConfigManager.getProperty("BASE_URI")).contentType(ContentType.JSON)
			.body(credentials).when().post("login").then()
			.log().ifValidationFails().statusCode(200).extract().jsonPath().getString("data.token");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
		
	}

}
