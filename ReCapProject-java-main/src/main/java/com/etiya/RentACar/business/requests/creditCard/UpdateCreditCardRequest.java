package com.etiya.RentACar.business.requests.creditCard;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.etiya.RentACar.business.constants.Messages;
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

	@Pattern(regexp="(\\d{16})",message = Messages.CREDITCARDNUMBERERROR)
	@NotNull
	private String cardNumber;

	@Pattern(regexp="(\\d{3})",message = Messages.CREDITCARDCVCERROR)
	@NotNull
	private String cvv;

	@NotNull
	private String expirationDate;

	@NotNull
	private int userId;

}
