package com.etiya.RentACar.business.requests.Rental;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

	private int rentalId;


	private LocalDate returnDate;

	private int returnCityId;

	private String endKm;
}
