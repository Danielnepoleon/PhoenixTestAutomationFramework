package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvReaderUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(CsvReaderUtil.class);
	
	private CsvReaderUtil() {
	}

	public static <T> Iterator<T> loadCsv(String path, Class<T> bean) {
		// TODO Auto-generated method stub
		LOGGER.info("Loading csv file from the path {}",path);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		LOGGER.info("Coverting csv to the bean class {}", bean);
		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader).withType(bean).withIgnoreEmptyLine(true).build();
		List<T> list = csvToBean.parse();
		return list.iterator();
	}

}
