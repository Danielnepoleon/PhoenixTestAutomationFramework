package com.api.constants;

public enum Products {
	
	NEXUS_2(1) , PIXEL(2);
	
	private int code;
	
	private Products(int code) {
		this.code= code;
		}
	
	public int getCode() {
		return code;
	}

}
