package com.etiya.RentACar.business.requests.Rental;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	
	@JsonIgnore
	private int id;
	
	private int carId;
	
	private int individualCustomerId;
	
	
	private LocalDate rentDate;
	
	
	private LocalDate returnDate;
	
}
