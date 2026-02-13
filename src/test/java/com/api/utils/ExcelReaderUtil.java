package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.Usercredentials;

public class ExcelReaderUtil {
	private static final Logger LOGGER = LogManager.getLogger(ExcelReaderUtil.class);
	private ExcelReaderUtil() {
	}

	public static Iterator<Usercredentials> loadExcelTestData(String fileName, String sheetName) {
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
		XSSFRow row = sheet.getRow(0);
		int usernameIndex = -1;
		int passwordIndex = -1;

		for (Cell cell : row) {
			if (cell.getStringCellValue().trim().equalsIgnoreCase("username")) {
				usernameIndex = cell.getColumnIndex();
			}
			if (cell.getStringCellValue().trim().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}
		}

		int lastRowIndex = sheet.getLastRowNum();
		XSSFRow rowData;
		Usercredentials creds=null;
		List<Usercredentials> list = new ArrayList<Usercredentials>();
		for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
			rowData = sheet.getRow(rowIndex);
			creds = new Usercredentials(rowData.getCell(usernameIndex).toString(),
					rowData.getCell(passwordIndex).toString());
			list.add(creds);
		}
		LOGGER.info("Data from file {} sheetname {} is converted to pojo {}", fileName, sheetName, creds);
		return list.iterator();

	}

}
