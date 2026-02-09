package com.database.dao;

import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		CustomerDBModel custdbm = CustomerDao.getCustomerInfo(175807);
//		System.out.println(custdbm);
		
		CustomerAddressDBModel custadbm = CustomerAddressDao.getCustomerAdressData(175807);
		System.out.println(custadbm);
	}

}
