package com.api.utils;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.filters.SensitiveDataFilter;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtils {

	@Step("Setting up the request configuration(Base uri, content type) ")
	public static RequestSpecification requestSpec() {
		RequestSpecification request = null;
		request = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured()).build();

		return request;
	}

	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification requestSpecification = null;
		requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilter()).addFilter(new AllureRestAssured()).build();

		return requestSpecification;
	}

	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification requestSpecification = null;
		requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenGenerator.getToken(role)).addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured()).build();

		return requestSpecification;
	}

	public static RequestSpecification requestSpecWithAuthBody(Role role, Object payload) {
		RequestSpecification requestSpecification = null;
		requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenGenerator.getToken(role)).setBody(payload)
				.addFilter(new SensitiveDataFilter()).addFilter(new AllureRestAssured()).build();

		return requestSpecification;
	}

	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(1000L)).build();

		return responseSpecification;
	}

	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L)).build();

		return responseSpecification;
	}

	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).build();

		return responseSpecification;
	}

}
