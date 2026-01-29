package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.poiji.bind.Poiji;

public class ExcelReaderPoijiUtil {

	private ExcelReaderPoijiUtil() {
	}

	public static <T> Iterator<T> loadExcelTestData(String fileName, String sheetName, Class<T> clazz) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		try {
			workbook = new XSSFWorkbook(is);
			sheet = workbook.getSheet(sheetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<T> dataList =  Poiji.fromExcel(sheet, clazz);
		
		return dataList.iterator();

	}

}
