package com.api.constants;

public enum WARRANTY_STATUS {
	IN_WARRANTY(1), OUT_WARRANTY(2);

	private int code;

	private WARRANTY_STATUS(int code) {

		this.code = code;
	}

	public int getCode() {
		return code;
	}

}
