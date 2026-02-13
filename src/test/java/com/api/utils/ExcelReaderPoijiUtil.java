package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderPoijiUtil {
	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderPoijiUtil.class);
	
	private ExcelReaderPoijiUtil() {
	}

	public static <T> Iterator<T> loadExcelTestData(String fileName, String sheetName, Class<T> clazz) {
		LOGGER.info("Reading data from the file {} and the sheeName is {}", fileName, sheetName);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		try {
			workbook = new XSSFWorkbook(is);
			sheet = workbook.getSheet(sheetName);
		} catch (IOException e) {
			LOGGER.error("Unable to read data from the file {} and the sheeName is {}", fileName, sheetName);
		}
		
		List<T> dataList =  Poiji.fromExcel(sheet, clazz);
		LOGGER.info("Data from file {} sheetname {} is converted to pojo {}", fileName, sheetName, clazz);
		return dataList.iterator();

	}

}
