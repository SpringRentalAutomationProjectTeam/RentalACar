package com.etiya.RentACar.business.requests.car;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

	@NotNull
	private int modelYear;

	@NotNull
	private int dailyPrice;

	@NotNull
	@Size(min = 2,max = 100)
	private String description;
	
	@NotNull
	private int colorId;

	@NotNull
	private int brandId;

	@NotNull
	private int cityId;

	

	
}
