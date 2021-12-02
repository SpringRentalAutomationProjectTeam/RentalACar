package com.etiya.RentACar.business.dtos;

import java.util.List;

import com.etiya.RentACar.entites.ComplexTypes.CarImageDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarSearchListDto {

	private int carId;
	
    private int modelYear;

    private int dailyPrice;

    private String description;
    
    private int minFindeksScore;

    private String  cityName;

}
