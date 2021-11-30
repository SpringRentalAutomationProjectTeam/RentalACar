package com.etiya.RentACar.business.requests.maintenance;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMaintenanceRequest {

	
	private int carId;
	
	private String description;
	
	private LocalDate maintenanceDate;
	
	
}
