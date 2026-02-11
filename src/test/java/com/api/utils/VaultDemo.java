package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VaultConfig config;
		Vault vault;
		try {
			config = new VaultConfig().address("http://13.232.186.218:8200/").token("root").build();
			vault = new Vault(config);
			LogicalResponse response =  vault.logical().read("secret/phoenix/qa/database");
			Map<String,String> dataMap = response.getData();
			System.out.println(dataMap.get("DB_URL"));
		} catch (VaultException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
