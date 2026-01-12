package com.api.constants;

public enum Platform {
	FST(3),
	FRONT_DESK(0);
	
	private int code;

	private Platform(int code) {

		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
