package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvReaderUtil {
	
	private CsvReaderUtil() {}
	
	public static <T> Iterator<T> loadCsv(String path , Class<T> bean) {
		// TODO Auto-generated method stub
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(bean)
				.withIgnoreEmptyLine(true)
				.build();
		List<T> list = csvToBean.parse();
		return list.iterator();  
	}

}
