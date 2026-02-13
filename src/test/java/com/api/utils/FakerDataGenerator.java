package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {
	private static final Logger LOGGER = LogManager.getLogger(FakerDataGenerator.class);
	private static Faker faker = new Faker(new Locale("en-IND"));
	private final static Random RANDOM = new Random();
	private final static int MST_SERVICE_LOCATION_ID = 0;
	private final static int MST_PLATFORM_ID = 2;
	private final static int MST_WARRENTY_STATUS_ID = 1;
	private final static int MST_OEM_ID = 1;
	private final static int PRODUCT_ID = 1;
	private final static int MST_MODEL_ID = 1;
	private final static int[] VALID_PROBLEM_ID = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 1, 17, 19, 20, 22, 24,
			26 };

	private FakerDataGenerator() {
	}

	public static CreateJobPayload generateFakeCreateJobData() {
		LOGGER.info("Generating fake payloads for createjob.....");
		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddressData();
		CustomerProduct customerProduct = generateFakeCustomerProductData();
		List<Problems> problems = generateFakeProblemsData();
		CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRENTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problems);
		return createJobPayload;

	}

	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {
		LOGGER.info("Generating {} fake payloads for createjob.....",count);
		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();
		Customer customer;
		CustomerAddress customerAddress;
		CustomerProduct customerProduct;
		List<Problems> problems;
		CreateJobPayload createJobPayload;
		for (int i = 0; i < count; i++) {
			customer = generateFakeCustomerData();
			customerAddress = generateFakeCustomerAddressData();
			customerProduct = generateFakeCustomerProductData();
			problems = generateFakeProblemsData();
			createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID, MST_WARRENTY_STATUS_ID,
					MST_OEM_ID, customer, customerAddress, customerProduct, problems);
			createJobPayloadList.add(createJobPayload);
		}
		return createJobPayloadList.iterator();

	}

	private static List<Problems> generateFakeProblemsData() {
		LOGGER.info("Generating fake payloads for problems data.....");
		int count = RANDOM.nextInt(3) + 1;
		int id;
		String remarks;
		Problems problem;
		List<Problems> problems = new ArrayList<Problems>();
		;
		for (int i = 0; i < count; i++) {
			id = RANDOM.nextInt(VALID_PROBLEM_ID.length);
			remarks = faker.lorem().sentence(3);
			problem = new Problems(VALID_PROBLEM_ID[id], remarks);
			problems.add(problem);
		}
		return problems;
	}

	private static CustomerProduct generateFakeCustomerProductData() {
		LOGGER.info("Generating fake payloads for customer product.....");
		String dop = DateTimeUtil.getTimeWithDaysAgo(10);
		String serial_number = faker.numerify("##############");
		String imei1 = faker.numerify("##############");
		String imei2 = faker.numerify("##############");
		String popurl = faker.internet().url();
		CustomerProduct customerProduct = new CustomerProduct(dop, serial_number, imei1, imei2, popurl, PRODUCT_ID,
				MST_MODEL_ID);
		return customerProduct;
	}

	private static Customer generateFakeCustomerData() {
		LOGGER.info("Generating fake payloads for customer data.....");
		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String mobileNumber = faker.numerify("704#######");
		String altMobileNumber = faker.numerify("740#######");
		String email = faker.internet().emailAddress();
		String altEmail = faker.internet().emailAddress();

		Customer customer = new Customer(fname, lname, mobileNumber, altMobileNumber, email, altEmail);
		return customer;
	}

	private static CustomerAddress generateFakeCustomerAddressData() {
		LOGGER.info("Generating fake payloads for customer address.....");
		String flat_number = faker.address().buildingNumber();
		String apartment_name = faker.address().streetPrefix();
		String street_name = faker.address().streetName();
		String landmark = faker.address().streetSuffix();
		String area = faker.address().city();
		int pincode = Integer.parseInt(faker.numerify("####"));
		String country = faker.address().country();
		String state = faker.address().state();
		CustomerAddress customerAddress = new CustomerAddress(flat_number, apartment_name, street_name, landmark, area,
				pincode, country, state);
		return customerAddress;
	}
}
