package com.etiya.RentACar.business.requests.Rental;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import com.etiya.RentACar.business.dtos.CreditCardDto;
import com.etiya.RentACar.business.dtos.CreditCardRentalDto;
import com.etiya.RentACar.business.requests.creditCard.CreateCreditCardRequest;
import com.etiya.RentACar.entites.CreditCard;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest  {

	private int rentalId;

	@NotNull
	@Pattern(regexp = "yyyy-MM-dd",message = "yyyy-MM-dd formatında girmelisiniz.")
	private LocalDate returnDate;

	private int returnCityId;

	private String endKm;

	private CreditCardRentalDto creditCard;

}
