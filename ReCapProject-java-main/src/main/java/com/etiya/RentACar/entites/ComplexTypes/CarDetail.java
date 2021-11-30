package com.etiya.RentACar.entites.ComplexTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetail {

	private int id;
	private String brandName;
	private String colorName;
	private int dailyPrice;
	private int modelYear;
	private String description;
	
}	
