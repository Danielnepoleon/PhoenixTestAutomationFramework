package com.api.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDbConfig {

	private static VaultConfig config;
	private static Vault vault;
	private static final Logger LOGGER = LogManager.getLogger(VaultDbConfig.class);

	private VaultDbConfig() {
	}

	static {
		try {
			LOGGER.info("Establishing connection to vault");
			config = new VaultConfig().address("http://13.232.186.218:8200/").token("root").build();
			vault = new Vault(config);
		} catch (VaultException e) {
			LOGGER.error("Something went wrong with vault config");
		}

	}

	public static String getSecret(String key) {
		LogicalResponse response = null;
		Map<String, String> dataMap = null;
		try {
			response = vault.logical().read("secret/phoenix/qa/database");
			dataMap = response.getData();
		} catch (VaultException e) {
			LOGGER.error("Something went wrong with reading vault secrets");
			return null;
		}
		LOGGER.info("Value for {} read from vault successfully", key);
		return dataMap.get(key);
	}

}
