package com.api.utils;

import static com.api.constants.Role.ENG;
import static com.api.constants.Role.FD;
import static com.api.constants.Role.QC;
import static com.api.constants.Role.SUP;
import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.request.model.Usercredentials;

import io.restassured.http.ContentType;

public class AuthTokenGenerator {

	private static Map<Role, String> tokenCache = new ConcurrentHashMap<Role, String>();
	private static final Logger LOGGER = LogManager.getLogger(AuthTokenGenerator.class);

	private AuthTokenGenerator() {

	}

	public static String getToken(Role role) {
		LOGGER.info("Checking if token is present for {}", role);
		if (tokenCache.containsKey(role)) {
			LOGGER.info("Token found for {}", role);
			return tokenCache.get(role);
		}
		LOGGER.info("Token not found, making login request for {}", role);
		Usercredentials credentials = null;
		if (role == FD) {
			credentials = new Usercredentials("iamfd", "password");
		} else if (role == SUP) {
			credentials = new Usercredentials("iamsup", "password");
		} else if (role == ENG) {
			credentials = new Usercredentials("iameng", "password");
		} else if (role == QC) {
			credentials = new Usercredentials("iamqc", "password");
		}
		String token = null;

		token = given().baseUri(ConfigManager.getProperty("BASE_URI")).contentType(ContentType.JSON).body(credentials)
				.when().post("login").then().log().ifValidationFails().statusCode(200).extract().jsonPath()
				.getString("data.token");
		tokenCache.put(role, token);
		LOGGER.info("Token cached for {} for future request", role);
		return token;

	}

}
