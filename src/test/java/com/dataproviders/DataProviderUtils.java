package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Usercredentials;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.CsvReaderUtil;
import com.api.utils.ExcelReaderPoijiUtil;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.database.dao.CreateJobApiPayloadDataDao;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {
	private static final Logger LOGGER = LogManager.getLogger(DataProviderUtils.class);

	@DataProvider(name = "LoginApiDataProvider", parallel = true)
	public static Iterator<UserBean> loginApiDataProvider() {
		LOGGER.info("Loading data from the CSV file testData/LoginCreds.csv");
		return CsvReaderUtil.loadCsv("testData/LoginCreds.csv", UserBean.class);
	}

	@DataProvider(name = "LoginApiJsonDataProvider", parallel = true)
	public static Iterator<Usercredentials> loginApiJsonDataProvider() {
		LOGGER.info("Loading data from the JSON file testData/LoginApiData.json");
		return JsonReaderUtil.loadJson("testData/LoginApiData.json", Usercredentials[].class);
	}

	@DataProvider(name = "LoginApiExcelDataProvider", parallel = true)
	public static Iterator<Usercredentials> loginApiExcelDataProvider() {
		LOGGER.info("Loading data from the Excel file testData/LoginApiExcelData.xlsx");
		return ExcelReaderUtil.loadExcelTestData("testData/LoginApiExcelData.xlsx", "Login Cred");
	}

	@DataProvider(name = "LoginApiExcelPoijiDataProvider", parallel = true)
	public static Iterator<UserBean> loginApiExcelPoijiDataProvider() {
		LOGGER.info("Loading data from the Excel file testData/LoginApiExcelData.xlsx");
		return ExcelReaderPoijiUtil.loadExcelTestData("testData/LoginApiExcelData.xlsx", "Login Cred", UserBean.class);
	}

	@DataProvider(name = "CreateJobDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {
		LOGGER.info("Loading data from the Excel file testData/CreateJobData.csv");
		Iterator<CreateJobBean> createJobBeanIterator = CsvReaderUtil.loadCsv("testData/CreateJobData.csv",
				CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (createJobBeanIterator.hasNext()) {
			tempBean = createJobBeanIterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);

		}
		return payloadList.iterator();
	}

	@DataProvider(name = "CreateJobApiFakeDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobApiFakeDataProvider() {
		int invocationCount = Integer.parseInt(System.getProperty("fakerCount", "5"));
		Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator.generateFakeCreateJobData(invocationCount);
		return payloadIterator;
	}

	@DataProvider(name = "CreateJobApiJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobApiJsonDataProvider() {
		LOGGER.info("Loading data from the JSON file testData/CreateJobApiJsonData.json");
		return JsonReaderUtil.loadJson("testData/CreateJobApiJsonData.json", CreateJobPayload[].class);
	}

	@DataProvider(name = "CreateJobDataApiPoijiProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobApiPoijiDataProvider() {
		LOGGER.info("Loading data from the Excel file testData/PhoenixTestData.xlsx");
		Iterator<CreateJobBean> createJobBeanIterator = ExcelReaderPoijiUtil
				.loadExcelTestData("testData/PhoenixTestData.xlsx", "CreateJobTestData", CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (createJobBeanIterator.hasNext()) {
			tempBean = createJobBeanIterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);

		}
		return payloadList.iterator();
	}

	@DataProvider(name = "CreateJobApiDBDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobApiDBDataProvider() {
		LOGGER.info("Loading data from DB");
		List<CreateJobBean> beanList = CreateJobApiPayloadDataDao.getCreateJobPayloadData();
		CreateJobPayload createJobPayload;
		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();
		for (CreateJobBean createJobBean : beanList) {
			createJobPayload = CreateJobBeanMapper.mapper(createJobBean);
			createJobPayloadList.add(createJobPayload);
		}
		return createJobPayloadList.iterator();
	}
}
