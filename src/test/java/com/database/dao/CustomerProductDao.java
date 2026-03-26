package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	private static final Logger LOGGER = LogManager.getLogger(CustomerProductDao.class);
	private static final String CUSTOMER_PRODUCT_QUERY = """
			SELECT mst_model_id, dop, popurl,imei1, imei2, serial_number FROM tr_customer_product tcp WHERE tcp.tr_customer_id =?;
			""";

	private CustomerProductDao() {
	}

	public static CustomerProductDBModel getCustomerProductInfo(int customerId) {
		Connection conn;
		PreparedStatement statement;
		ResultSet resultSet;
		CustomerProductDBModel customerProductDBModel=null;
		try {
			LOGGER.info("Getting the db connection from database manager");
			conn = DatabaseManager.getConnection();
			statement = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			statement.setInt(1, customerId);
			LOGGER.info("Executing the sql query {} ...", CUSTOMER_PRODUCT_QUERY);
			resultSet = statement.executeQuery(CUSTOMER_PRODUCT_QUERY);
			while (resultSet.next()) {
				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("mst_model_id"),
						resultSet.getDate("dop"), resultSet.getString("popurl"), resultSet.getString("imei1"),
						resultSet.getString("imei2"), resultSet.getString("serial_number"));
			}

		} catch (SQLException e) {
			LOGGER.error("Cannot convert resultset to a model class ", e);
		}
		return customerProductDBModel;
	}

}
