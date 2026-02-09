package com.database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {

	private static final String CUSTOMER_PRODUCT_QUERY = """
			SELECT mst_model_id, dop, popurl,imei1, imei2, serial_number FROM tr_customer_product tcp WHERE tcp.tr_customer_id =?;
			""";

	private CustomerProductDao() {
	}

	public static CustomerProductDBModel getCustomerProductInfo(int customerId) {
		Connection conn;
		PreparedStatement statement;
		ResultSet resultSet;
		CustomerProductDBModel customerProductDBModel;
		try {
			conn = DatabaseManager.getConnection();
			statement = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			statement.setInt(1, customerId);
			resultSet = statement.executeQuery(CUSTOMER_PRODUCT_QUERY);
			while (resultSet.next()) {
				customerProductDBModel = new CustomerProductDBModel(resultSet.getInt("mst_model_id"),
						resultSet.getDate("dop"), resultSet.getString("popurl"), resultSet.getString("imei1"),
						resultSet.getString("imei2"), resultSet.getString("serial_number"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
