package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.api.request.model.Usercredentials;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {

	public static <T> Iterator<T> loadJson(String fileName, Class<T[]> clazz) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		ObjectMapper om = new ObjectMapper();
		T[] classArray;
		List<T> classList = null;
		try {
			classArray = om.readValue(is, clazz);
			classList = Arrays.asList(classArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classList.iterator();
	}
}
