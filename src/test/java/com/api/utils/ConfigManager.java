package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {

	private static Properties prop = new Properties();
	private static String filePath;
	private static String env;
	private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);

	private ConfigManager() {

	}

	static {
//		File configFile = new File(System.getProperty("user.dir") + File.separator+"src"+ File.separator+"test"+ File.separator+"resources"+ File.separator+"config"+ File.separator+"config.properties");
//		FileReader reader = null;
//		try {
//			reader = new FileReader(configFile);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		LOGGER.info("Reading the env value passed from the terminal");
		if (env == null) {
			LOGGER.warn("Env variable is not set...using qa as the environment");
		}
		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();
		LOGGER.info("Running the test in {} env", env);
		switch (env) {
		case "dev" -> filePath = "config/config.dev.properties";

		case "qa" -> filePath = "config/config.qa.properties";

		case "prod" -> filePath = "config/config.prod.properties";

		default -> filePath = "config/config.properties";
		}
		LOGGER.info("Running the test in {} env", env);
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		if (inputStream == null) {
			LOGGER.error("Cannot find file at the give path {}", filePath);
			System.err.println("File path for properties file is invalid");
		}
		try {
			prop.load(inputStream);
			LOGGER.info("File {} is loaded", filePath);
		} catch (IOException e) {
			LOGGER.error("Something went wrong {}..check the file path {}", e, filePath);
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

}
