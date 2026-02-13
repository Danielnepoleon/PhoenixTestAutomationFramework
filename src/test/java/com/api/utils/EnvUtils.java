package com.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvUtils {

	private static Dotenv dotEnv;
	private static final Logger LOGGER = LogManager.getLogger(EnvUtils.class);
	
	private EnvUtils() {
		
	}
	
	static {
		LOGGER.info("Loading .env file");
		dotEnv = Dotenv.load();
	}

	public static String getValue(String key) {
		LOGGER.info("Reading the vale of {} from .env file",key);
		return dotEnv.get(getValue(key));
	} 
}
