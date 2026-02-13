package com.listeners;

import java.util.Arrays;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.api.utils.AllureEnvironmentWriterUtil;

public class ApiItestListener implements ITestListener {

	private static final Logger LOGGER = LogManager.getLogger(ApiItestListener.class);

	public void onStart(ITestContext context) {
		LOGGER.info("******************************Starting Phoenix Framework Execution******************************");
		AllureEnvironmentWriterUtil.createAllureEnvironmentPropertyFile();
	}

	public void onTestStart(ITestResult result) {
		LOGGER.info("*******************************************************************************************");
		LOGGER.info("~~~~~~~~~~~~~ {} - Test started ~~~~~~~~~~~~~", result.getName());
		LOGGER.info("~~~~~~~~~~~~~ Test class details : {} ~~~~~~~~~~~~~", result.getMethod().getTestClass());
		LOGGER.info("~~~~~~~~~~~~~ Test description : {} ~~~~~~~~~~~~~", result.getMethod().getDescription());
		LOGGER.info("~~~~~~~~~~~~~ Test group details : {} ~~~~~~~~~~~~~",
				Arrays.toString(result.getMethod().getGroups()));
	}

	public void onTestSuccess(ITestResult result) {
		long testDuration = result.getEndMillis() - result.getStartMillis();

		LOGGER.info("~~~~~~~~~~~~~ Test duration : {} ms ~~~~~~~~~~~~~", testDuration);
		LOGGER.info("~~~~~~~~~~~~~ {} - Test Passed ! ~~~~~~~~~~~~~", result.getName());
		LOGGER.info("*******************************************************************************************");
	}

	public void onTestFailure(ITestResult result) {
		long testDuration = result.getEndMillis() - result.getStartMillis();

		LOGGER.error("~~~~~~~~~~~~~ Test duration : {} ms ~~~~~~~~~~~~~", testDuration);
		LOGGER.error("~~~~~~~~~~~~~ {} - Test Failed ! ~~~~~~~~~~~~~", result.getName());
		LOGGER.error("~~~~ Error message : {} ~~~~", result.getThrowable().getMessage());
		LOGGER.error(result.getThrowable());
		LOGGER.error("*******************************************************************************************");
	}

	public void onTestSkipped(ITestResult result) {
		LOGGER.error("~~~~~~~~~~~~~ {} - Test Skipped ! ~~~~~~~~~~~~~", result.getName());
		LOGGER.error(result.getThrowable());
		LOGGER.error("*******************************************************************************************");
	}

	public void onFinish(ITestContext context) {
		LOGGER.info(
				"******************************Phoenix Framework Execution is completed******************************");
	}
}
