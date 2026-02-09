package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

public class CustomerAddressDao {

	private static final String CUSTOMER_ADDRESS_QUERY = """
			SELECT id,
			flat_number,
			apartment_name,
			street_name,
			landmark,
			area,
			pincode,
			country,
			state FROM tr_customer_address where id =?;
			""";

	private CustomerAddressDao() {
	}

	public static CustomerAddressDBModel getCustomerAdressData(int customerId) {
		Connection conn;
		PreparedStatement statement;
		ResultSet resultSet;
		CustomerAddressDBModel customerAddressDBModel = null;
		try {
			conn = DatabaseManager.getConnection();
			statement = conn.prepareStatement(CUSTOMER_ADDRESS_QUERY);
			statement.setInt(1, customerId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				customerAddressDBModel = new CustomerAddressDBModel(resultSet.getInt("id"),
						resultSet.getString("flat_number"), resultSet.getString("apartment_name"),
						resultSet.getString("street_name"), resultSet.getString("landmark"),
						resultSet.getString("area"), resultSet.getInt("pincode"), resultSet.getString("country"),
						resultSet.getString("state"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerAddressDBModel;
	}

}
