package com.etiya.RentACar.business.requests.creditCard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.etiya.RentACar.business.requests.LoginRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
	
	@NotNull
	private int creditCardId;
	
	@NotNull
	@Size(max=40)
	private String name;

	@Pattern(regexp="(\\d{16})")
	@NotNull
	private String cardNumber;
	
	@Size(min=3,max=3)
	@NotNull
	private String cvv;
	
	@NotNull
	private int userId;

}
