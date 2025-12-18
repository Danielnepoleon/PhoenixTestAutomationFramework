package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.AuthTokenGenerator;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtils;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateJobApiTest {
	
	@Test
	public void createJobApiTest() throws IOException {
		
		CustomerAddress customerAddress = new CustomerAddress("6b", "apart", "Thisara perera", "cbi office", "puluk", "621232", "India", "Bihar");
		CustomerProduct customerProduct = new CustomerProduct("2025-09-30T18:30:00.000Z", "10192371917300","10132371017301", "10132371817302", "2025-09-30T18:30:00.000Z", 1, 2);
		Problems problems = new Problems(1, "Poor battery");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);
		Customer customer = new  Customer("Ayush","Mhatre","1243546789","","wery@gmail.com","");
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
		given().spec(SpecUtils.requestSpecWithAuthBody(Role.FD, createJobPayload)).when().post("/job/create")
		.then().spec(SpecUtils.responseSpec())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobApiSchema.json"))
		.body("message", equalTo("Job created successfully. "))
		.body("data.job_number", startsWith("JOB_"));
		
	}
	

}
