package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDbConfig {

	private static VaultConfig config;
	private static Vault vault;

	private VaultDbConfig() {
	}

	static {
		try {
			config = new VaultConfig().address("http://13.232.186.218:8200/").token("root").build();
			vault = new Vault(config);
		} catch (VaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getSecret(String key) {
		LogicalResponse response = null;
		Map<String, String> dataMap = null;
		try {
			response = vault.logical().read("secret/phoenix/qa/database");
			dataMap = response.getData();
		} catch (VaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return dataMap.get(key);
	}

}
