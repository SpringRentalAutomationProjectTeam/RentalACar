package com.etiya.RentACar.business.requests.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

	private int carId;
	
	private int colorId;

	private int brandId;

	private int modelYear;

	private int dailyPrice;

	private String description;

	private int cityId;
}
