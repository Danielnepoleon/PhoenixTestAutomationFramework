package com.api.constants;

public enum Models {
	
	NEXUS_2_BLUE(1) , GALLEXY(2);
	
	private int code;
	private Models(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

}
