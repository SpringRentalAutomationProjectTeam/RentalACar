package com.etiya.RentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceSearchListDto {


	private int id;
	
	private int carId;
	
	private String description;
	
	private LocalDate maintenanceDate;
	
	private LocalDate returnDate;
}
