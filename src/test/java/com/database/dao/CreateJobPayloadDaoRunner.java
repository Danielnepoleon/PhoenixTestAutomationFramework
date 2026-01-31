package com.database.dao;

import java.util.ArrayList;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;

public class CreateJobPayloadDaoRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<CreateJobBean> beanList = CreateJobApiPayloadDataDao.getCreateJobPayloadData();
		CreateJobPayload createJobPayload;
		List<CreateJobPayload> createJobPayloadList = new ArrayList<CreateJobPayload>();
		for (CreateJobBean createJobBean : beanList) {
			createJobPayload = CreateJobBeanMapper.mapper(createJobBean);
			createJobPayloadList.add(createJobPayload);
		}
		
		for (CreateJobPayload createJobPayload2 : createJobPayloadList) {
			System.out.println(createJobPayload2);
		}
	}

}
