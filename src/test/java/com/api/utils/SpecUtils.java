package com.api.utils;

import java.io.IOException;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.pojo.Usercredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtils {

	public static RequestSpecification requestSpec() {
		RequestSpecification request = null;
		try {
			request = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
					.setContentType(ContentType.JSON).setAccept(ContentType.JSON).log(LogDetail.URI)
					.log(LogDetail.HEADERS).log(LogDetail.METHOD).log(LogDetail.BODY).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return request;
	}

	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = null;
		try {
			requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
					.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
					.log(LogDetail.URI).log(LogDetail.HEADERS).log(LogDetail.METHOD).log(LogDetail.BODY).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return requestSpecification;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = null;
		try {
			requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
					.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(AuthTokenGenerator.getToken(role))
					.log(LogDetail.URI).log(LogDetail.HEADERS).log(LogDetail.METHOD).log(LogDetail.BODY).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return requestSpecification;
	}

	public static ResponseSpecification responseSpec() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();

		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpecWithStatusCheck(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();

		return responseSpecification;
	}

}
