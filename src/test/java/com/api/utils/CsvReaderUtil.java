package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvReaderUtil {
	
	private CsvReaderUtil() {}
	
	public static void loadCsv(String path) {
		// TODO Auto-generated method stub
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(UserBean.class)
				.withIgnoreEmptyLine(true)
				.build();
		List<UserBean> userList = csvToBean.parse();
		System.out.println(userList);
	}

}
