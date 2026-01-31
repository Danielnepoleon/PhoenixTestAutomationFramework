package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();
	private static String filePath;
	private static String env;

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
		env = System.getProperty("env", "qa");
		env = env.toLowerCase().trim();
		switch (env) {
		case "dev" -> filePath = "config/config.dev.properties";

		case "qa" -> filePath = "config/config.qa.properties";

		case "prod" -> filePath = "config/config.prod.properties";

		default -> filePath = "config/config.properties";
		}
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		if (inputStream == null) {
			System.err.println("File path for properties file is invalid");
		}
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}

}
