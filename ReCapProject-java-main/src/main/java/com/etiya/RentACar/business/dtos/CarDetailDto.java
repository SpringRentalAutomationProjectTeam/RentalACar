package com.etiya.RentACar.business.dtos;

import java.util.List;

import com.etiya.RentACar.entites.ComplexTypes.CarImageDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetailDto {
	private String brandName;
	private String colorName;
	private int dailyPrice;
	private int modelYear;
	private String description;
    private List<CarImagesDto> carImagesDetail;
	//private List<String> imagePaths;
}
