package com.database.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerProductDBModel {
	
	private int mst_model_id;
	private Date dop;
	private String popurl;
	private String imei1;
	private String imei2;
	private String serial_number;

}
