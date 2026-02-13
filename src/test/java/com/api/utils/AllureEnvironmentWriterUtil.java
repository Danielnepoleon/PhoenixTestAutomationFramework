package com.api.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AllureEnvironmentWriterUtil {
	private static Logger LOGGER = LogManager.getLogger(AllureEnvironmentWriterUtil.class);
	
	public static void createAllureEnvironmentPropertyFile() {
		
		String folderPath = "target/allure-results";
		File file = new File(folderPath);
		file.mkdir();
		Properties prop = new Properties();
		prop.setProperty("Name", "Daniel");
		prop.setProperty("Project Name", "Phoenix Test Automation Framework");
		prop.setProperty("Env", ConfigManager.env);
		prop.setProperty("Base_URI", ConfigManager.getProperty("BASE_URI"));
		prop.setProperty("Operation System", System.getProperty("os.name"));
		prop.setProperty("Operation System", System.getProperty("os.version"));
		prop.setProperty("Operation System", System.getProperty("java.version"));
		FileWriter fw;
		try {
			fw = new FileWriter(folderPath+"/environment.properties");
			prop.store(fw, "My properties file");
			LOGGER.info("Created environment.properties file for allure at {}",folderPath);
		} catch (IOException e) {
			LOGGER.error("Unable to create environment.properties files for allure", e);
		}
		
	}

}
