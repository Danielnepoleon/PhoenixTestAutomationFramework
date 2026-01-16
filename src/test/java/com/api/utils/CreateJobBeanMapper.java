package com.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.dataproviders.api.bean.CreateJobBean;

public class CreateJobBeanMapper {

	private CreateJobBeanMapper() {
	}

	public static CreateJobPayload mapper(CreateJobBean bean) {
		int mst_service_location_id = Integer.parseInt(bean.getMst_service_location_id());
		int mst_platform_id = Integer.parseInt(bean.getMst_platform_id());
		int mst_oem_id = Integer.parseInt(bean.getMst_oem_id());
		int mst_warrenty_status_id = Integer.parseInt(bean.getMst_warrenty_status_id());
		Customer customer = new Customer(bean.getCustomer__first_name(), bean.getCustomer__last_name(),
				bean.getCustomer__mobile_number(), bean.getCustomer__mobile_number_alt(), bean.getCustomer__email_id(),
				bean.getCustomer__mobile_number_alt());
		int pincode = Integer.parseInt(bean.getCustomer_address__pincode());
		CustomerAddress customer_address = new CustomerAddress(bean.getCustomer_address__flat_number(),
				bean.getCustomer_address__apartment_name(), bean.getCustomer_address__street_name(),
				bean.getCustomer_address__landmark(), bean.getCustomer_address__area(),
				pincode, bean.getCustomer_address__country(),
				bean.getCustomer_address__state());
		int productId = Integer.parseInt(bean.getCustomer_product__product_id());
		int modelId = Integer.parseInt(bean.getCustomer_product__mst_model_id());
		CustomerProduct customerProduct = new CustomerProduct(bean.getCustomer_product__dop(),
				bean.getCustomer_product__serial_number(), bean.getCustomer_product__imei1(),
				bean.getCustomer_product__imei2(), bean.getCustomer_product__popurl(), productId, modelId);
		int id = Integer.parseInt(bean.getProblems__id());
		List<Problems> problemList = new ArrayList<Problems>();
		Problems problems = new Problems(id, bean.getProblems__remark());
		problemList.add(problems);
		CreateJobPayload payload = new CreateJobPayload(mst_service_location_id, mst_platform_id,
				mst_warrenty_status_id, mst_oem_id, customer, customer_address, customerProduct, problemList);

		return payload;

	}

}
