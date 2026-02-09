package com.api.responsemodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CreateJobResponseModel {
	private String message;
	private CreateJobDataModel data;

}
