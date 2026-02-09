package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static com.api.utils.SpecUtils.requestSpecWithAuthBody;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

public class CreateJobApiTestWithDbValidation {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;

	@BeforeMethod(description = "Creating payload for create job API")
	public void setUp() {
		customerAddress = new CustomerAddress("6b", "apart", "Thisara perera", "cbi office", "puluk", 621232, "India",
				"Bihar");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "10000671917300",
				"10000671017301", "10000061817302", getTimeWithDaysAgo(10), Products.NEXUS_2.getCode(),
				Models.GALLEXY.getCode());
		Problems problems = new Problems(Problem.OVERHEATING.getCode(), "Poor battery");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);
		customer = new Customer("Ayush", "Mhatre", "1243546789", "", "wery@gmail.com", "");
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), WARRANTY_STATUS.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemsList);
	}

	@Test(description = "Verify if the create job api is able to create Inwarranty job", groups = { "api", "regression",
			"smoke" })
	public void createJobApiTest() throws IOException {
		int customerId = given().spec(requestSpecWithAuthBody(Role.FD, createJobPayload)).when().post("/job/create")
				.then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobApiSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"))
				.extract().body().jsonPath().getInt("data.tr_customer_id");
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		System.out.println();

		Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
		Assert.assertEquals(customer.email_id(), customerDataFromDB.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), customerDataFromDB.getEmail_id_alt());
		Assert.assertEquals(customer.mobile_number(), customerDataFromDB.getMobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), customerDataFromDB.getMobile_number_alt());

		CustomerAddressDBModel customerAddressDataFromDB = CustomerAddressDao
				.getCustomerAdressData(customerDataFromDB.getTr_customer_address_id());
		Assert.assertEquals(customerAddress.flat_number(), customerAddressDataFromDB.getFlat_number());
		Assert.assertEquals(customerAddress.apartment_name(), customerAddressDataFromDB.getApartment_name());
		Assert.assertEquals(customerAddress.street_name(), customerAddressDataFromDB.getStreet_name());
		Assert.assertEquals(customerAddress.landmark(), customerAddressDataFromDB.getLandmark());
		Assert.assertEquals(customerAddress.area(), customerAddressDataFromDB.getArea());
		Assert.assertEquals(customerAddress.pincode(), customerAddressDataFromDB.getPincode());
		Assert.assertEquals(customerAddress.country(), customerAddressDataFromDB.getCountry());
		Assert.assertEquals(customerAddress.state(), customerAddressDataFromDB.getState());
		
		CustomerProductDBModel customerProductDBModel = CustomerProductDao.getCustomerProductInfo(customerId);
		
		Assert.assertEquals(customerProduct.mst_model_id() , customerProductDBModel.getMst_model_id());
		Assert.assertEquals(customerProduct.dop() , customerProductDBModel.getDop());
		Assert.assertEquals(customerProduct.popurl() , customerProductDBModel.getPopurl());
		Assert.assertEquals(customerProduct.imei1() , customerProductDBModel.getImei1());
		Assert.assertEquals(customerProduct.imei2() , customerProductDBModel.getImei2());
		Assert.assertEquals(customerProduct.serial_number() , customerProductDBModel.getSerial_number());
		
		

	}

}
