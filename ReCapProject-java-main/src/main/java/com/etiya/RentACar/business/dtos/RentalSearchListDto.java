package com.etiya.RentACar.business.dtos;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalSearchListDto {
	
	private int rentalId;
	
	private LocalDate rentDate;
	
	private LocalDate returnDate;
	
	private int carId;

	private int userId;

	private String startKm;

	private String endKm;

	private String rentCity;

	private String returnCity;
	
}
