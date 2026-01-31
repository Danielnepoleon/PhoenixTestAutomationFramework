package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.database.DatabaseManager;
import com.dataproviders.api.bean.CreateJobBean;

public class CreateJobApiPayloadDataDao {
	private static final String SQL_QUERY = """
						select tc.first_name,
			tc.last_name,
			tc.email_id,
			tc.email_id_alt,
			tc.mobile_number,
			tc.mobile_number_alt,
			tca.flat_number ,
			tca.apartment_name ,
			tca.street_name ,
			tca.landmark ,
			tca.area ,
			tca.pincode ,
			tca.country ,
			tca.state ,
			tcp.mst_model_id,
			tcp.dop,
			tcp.popurl,
			tcp.imei2,
			tcp.imei1,
			tcp.serial_number,
			tjh.tr_customer_product_id ,
			tjh.mst_service_location_id,
			tjh.mst_platform_id,
			tjh.mst_warrenty_status_id,
			tjh.mst_oem_id,
			mjp.mst_problem_id,
			mjp.remark
			FROM tr_customer tc
			inner join tr_customer_address tca ON tc.id = tca.id
			inner join tr_customer_product tcp ON tc.id = tcp.id
			inner join tr_job_head tjh ON tc.id = tjh.tr_customer_id
			inner join map_job_problem mjp ON mjp.tr_job_head_id   = tjh.id LIMIT 5;
						""";

	public static List<CreateJobBean> getCreateJobPayloadData() {
		Connection conn = null;
		Statement statement;
		ResultSet resultSet;
		CreateJobBean bean = new CreateJobBean();
		List<CreateJobBean> beanList = new ArrayList<CreateJobBean>();
		try {
			conn = DatabaseManager.getConnection();
			statement = conn.createStatement();
			resultSet = statement.executeQuery(SQL_QUERY);
			while (resultSet.next()) {
				bean.setCustomer__first_name(resultSet.getString("first_name"));
				bean.setCustomer__last_name(resultSet.getString("last_name"));
				bean.setCustomer__email_id(resultSet.getString("email_id"));
				bean.setCustomer__email_id_alt(resultSet.getString("email_id_alt"));
				bean.setCustomer__mobile_number(resultSet.getString("mobile_number"));
				bean.setCustomer__mobile_number_alt(resultSet.getString("mobile_number_alt"));
				bean.setCustomer_address__flat_number(resultSet.getString("flat_number"));
				bean.setCustomer_address__apartment_name(resultSet.getString("apartment_name"));
				bean.setCustomer_address__street_name(resultSet.getString("street_name"));
				bean.setCustomer_address__landmark(resultSet.getString("landmark"));
				bean.setCustomer_address__area(resultSet.getString("area"));
				bean.setCustomer_address__pincode(resultSet.getString("pincode"));
				bean.setCustomer_address__country(resultSet.getString("country"));
				bean.setCustomer_address__state(resultSet.getString("state"));
				bean.setCustomer_product__mst_model_id("1");
				bean.setCustomer_product__dop(resultSet.getString("dop"));
				bean.setCustomer_product__popurl(resultSet.getString("popurl"));
				bean.setCustomer_product__imei1(resultSet.getString("imei1"));
				bean.setCustomer_product__imei2(resultSet.getString("imei2"));
				bean.setCustomer_product__serial_number(resultSet.getString("serial_number"));
				bean.setMst_service_location_id(resultSet.getString("mst_service_location_id"));
				bean.setCustomer_product__product_id("1");
				bean.setMst_platform_id(resultSet.getString("mst_platform_id"));
				bean.setMst_warrenty_status_id(resultSet.getString("mst_warrenty_status_id"));
				bean.setMst_oem_id("1");
				bean.setProblems__id(resultSet.getString("mst_problem_id"));
				bean.setProblems__remark(resultSet.getString("remark"));
				beanList.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beanList;

	}

}
