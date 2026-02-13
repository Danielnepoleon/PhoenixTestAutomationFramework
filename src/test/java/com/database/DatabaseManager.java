package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtils;
import com.api.utils.VaultDbConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USERNAME = loadSecret("DB_USERNAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");
	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;
	private static final String MAXIMUM_POOL_SIZE = ConfigManager.getProperty("MAXIMUM_POOL_SIZE");
	private static final String MINIMUM_IDLE = ConfigManager.getProperty("MINIMUM_IDLE");
	private static final String CONNECTION_TIME_OUT_IN_SEC = ConfigManager.getProperty("CONNECTION_TIME_OUT_IN_SEC");
	private static final String IDLE_TIME_OUT_IN_SEC = ConfigManager.getProperty("IDLE_TIME_OUT_IN_SEC");
	private static final String MAXIMUM_LIFETIME_IN_SEC = ConfigManager.getProperty("MAXIMUM_LIFETIME_IN_SEC");
	private static final String POOL_NAME = ConfigManager.getProperty("POOL_NAME");
	private static Connection conn;

	private DatabaseManager() {
	}

	private static void initializePool() throws SQLException {
		if (hikariDataSource == null) {
			LOGGER.info("Database connection is not available....creating hikari data source");
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(Integer.parseInt(MAXIMUM_POOL_SIZE));
					hikariConfig.setMinimumIdle(Integer.parseInt(MINIMUM_IDLE));
					hikariConfig.setConnectionTimeout(Integer.parseInt(CONNECTION_TIME_OUT_IN_SEC) * 1000);
					hikariConfig.setIdleTimeout(Integer.parseInt(IDLE_TIME_OUT_IN_SEC) * 1000);
					hikariConfig.setMaxLifetime(Integer.parseInt(MAXIMUM_LIFETIME_IN_SEC) * 1000);
					hikariConfig.setPoolName(POOL_NAME);
					hikariDataSource = new HikariDataSource(hikariConfig);
					LOGGER.info("Hikari data source is created");
				}
			}

		}
	}

	public static Connection getConnection() throws SQLException {
		if (hikariDataSource == null) {
			LOGGER.info("Initializing the database connection using hikari data source");
			initializePool();
		} else if (hikariDataSource.isClosed()) {
			throw new SQLException("Hikari Data source is closed");
		}
		conn = hikariDataSource.getConnection();
		return conn;
	}

	public static String loadSecret(String key) {

		String value = null;

		if (isVaultUp) {
			value = VaultDbConfig.getSecret(key);

			if (value == null) {
				LOGGER.error("Vault is down! or some isse with vault");
			} else {
				LOGGER.info("Reading value from vault");
				return value;
			}
		}
		LOGGER.info("Reading values from .env file....");
		value = EnvUtils.getValue(key);
		return value;

	}

}
