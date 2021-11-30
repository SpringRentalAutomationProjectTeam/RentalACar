package com.etiya.RentACar.business.requests.Rental;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	
	@NotNull
	private int rentalId;
	
	@NotNull
	private int carId;
	@NotNull
	private int userId;
	@NotNull
	private LocalDate returnDate;
}
