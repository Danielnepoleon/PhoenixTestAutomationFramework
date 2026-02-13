package com.api.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoLogRunner {

	private static Logger logger = LogManager.getLogger(DemoLogRunner.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a =  10;
		int b=0;
		
		logger.info("Int a "+a);
		

	}

}
