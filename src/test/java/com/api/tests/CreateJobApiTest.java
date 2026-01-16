package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Models;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Products;
import com.api.constants.Role;
import com.api.constants.ServiceLocation;
import com.api.constants.WARRANTY_STATUS;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.SpecUtils.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobApiTest {

	private CreateJobPayload createJobPayload;

	@BeforeMethod(description = "Creating payload for create job API")
	public void setUp() {
		CustomerAddress customerAddress = new CustomerAddress("6b", "apart", "Thisara perera", "cbi office", "puluk",
				621232, "India", "Bihar");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "10102371917300",
				"10102371017301", "10132371817302", getTimeWithDaysAgo(10), Products.NEXUS_2.getCode(),
				Models.GALLEXY.getCode());
		Problems problems = new Problems(Problem.OVERHEATING.getCode(), "Poor battery");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);
		Customer customer = new Customer("Ayush", "Mhatre", "1243546789", "", "wery@gmail.com", "");
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), WARRANTY_STATUS.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemsList);
	}

	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = { "api", "regression", "smoke" })
	public void createJobApiTest() throws IOException {
		given().spec(requestSpecWithAuthBody(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK()).body(matchesJsonSchemaInClasspath("response-schema/CreateJobApiSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"));

	}

}
