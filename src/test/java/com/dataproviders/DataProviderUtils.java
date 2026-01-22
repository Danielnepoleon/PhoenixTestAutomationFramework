package com.dataproviders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Usercredentials;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.CsvReaderUtil;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name = "LoginApiDataProvider", parallel = true)
	public static Iterator<UserBean> loginApiDataProvider() {
		return CsvReaderUtil.loadCsv("testData/LoginCreds.csv", UserBean.class);
	}
	
	@DataProvider(name = "LoginApiJsonDataProvider", parallel = true)
	public static Iterator<Usercredentials> loginApiJsonDataProvider() {
		return JsonReaderUtil.loadJson("testData/LoginApiData.json", Usercredentials[].class);
	}

	@DataProvider(name = "CreateJobDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobDataProvider() {

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
		return JsonReaderUtil.loadJson("testData/CreateJobApiJsonData.json", CreateJobPayload[].class);
	}
}
