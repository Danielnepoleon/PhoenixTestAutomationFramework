package com.api.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtils {

	private static Dotenv dotEnv = Dotenv.load();

	private EnvUtils() {
		
	}

	public static String getValue(String key) {
		return dotEnv.get(getValue(key));
	} 
}
