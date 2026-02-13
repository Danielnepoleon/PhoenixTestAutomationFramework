package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {
	private static final Logger LOGGER = LogManager.getLogger(JsonReaderUtil.class);
	public static <T> Iterator<T> loadJson(String fileName, Class<T[]> clazz) {
		LOGGER.info("Reading the json file {} ", fileName);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		ObjectMapper om = new ObjectMapper();
		T[] classArray;
		List<T> classList = null;
		try {
			LOGGER.info("Converting the json to bean class {} ", clazz);
			classArray = om.readValue(is, clazz);
			classList = Arrays.asList(classArray);
		} catch (IOException e) {
			LOGGER.error("Unable to convert json to bean class {} ", clazz);
		}
		return classList.iterator();
	}
}
