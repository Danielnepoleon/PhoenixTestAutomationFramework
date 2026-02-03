package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {

	private static final String CUSTOMER_DETAILS_QUERY = """
			select * from tr_customer tc where tc.id = ? ;
			""";

	public static CustomerDBModel getCustomerInfo(int customerId) {
		
		CustomerDBModel custDbm = null;
		Connection conn;
		PreparedStatement statement;
		ResultSet resultset;
		try {
			conn = DatabaseManager.getConnection();
			statement = conn.prepareStatement(CUSTOMER_DETAILS_QUERY);
			statement.setInt(1, customerId );
			resultset = statement.executeQuery();
			while (resultset.next()) {
				System.out.println(resultset.getString("first_name"));
				System.out.println(resultset.getString("email_id"));
				custDbm = new CustomerDBModel(resultset.getString("first_name"),
						resultset.getString("last_name"), resultset.getString("mobile_number"),
						resultset.getString("mobile_number_alt"), resultset.getString("email_id"),
						resultset.getString("email_id_alt"), resultset.getString("tr_customer_address_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return custDbm;
	}

}