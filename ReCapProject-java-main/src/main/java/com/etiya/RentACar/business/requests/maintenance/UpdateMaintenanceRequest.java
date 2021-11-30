package com.etiya.RentACar.business.requests.maintenance;


import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMaintenanceRequest {

	private int maintenanceId;
	
	private int carId;

	private String description;	
	
	private LocalDate returnDate;
}
