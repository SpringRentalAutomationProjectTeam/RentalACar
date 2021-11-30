package com.etiya.RentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalSearchListDto {
	
	private int id;
	private LocalDate returnDate;
	
}
