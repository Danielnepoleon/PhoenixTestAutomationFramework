package com.api.filters;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		LOGGER.info("******************** Request Details ********************");
		LOGGER.info("Base Uri : {}", requestSpec.getURI());
		LOGGER.info("HTTP method : {}", requestSpec.getMethod());
		redactHeader(requestSpec);
		redactRequestPayload(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);
		LOGGER.info("******************** Response Details ********************");
		LOGGER.info("Status : {}", response.statusLine());
		LOGGER.info("Response time : {}", response.timeIn(TimeUnit.MILLISECONDS));
		redactResponseBody(response);
		return response;
	}

	public static void redactHeader(FilterableRequestSpecification requestSpec) {
		List<Header> headers = requestSpec.getHeaders().asList();

		for (Header header : headers) {
			if (header.getName().equalsIgnoreCase("Authorization")) {
				LOGGER.info("Header {} : {}", header.getName(), "\"[Redacted]\"");
			} else {
				LOGGER.info("Header {} : {}", header.getName(), header.getValue());
			}
		}
	}

	public void redactRequestPayload(FilterableRequestSpecification requestSpec) {
		if(requestSpec.getBody()!=null) {
		String requestPayload = requestSpec.getBody().toString();
		requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\" : \"[Redacted]\"");
		LOGGER.info("\nRequest payload : {}", requestPayload);
		}
	}

	private void redactResponseBody(Response response) {
		String responseBody = response.asPrettyString();
		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\" : \"[Redacted]\"");
		LOGGER.info("\nResponse body : {}", responseBody);

	}

}
