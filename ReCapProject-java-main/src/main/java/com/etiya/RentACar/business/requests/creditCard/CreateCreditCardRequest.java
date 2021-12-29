package com.etiya.RentACar.business.requests.creditCard;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.etiya.RentACar.business.constants.Messages;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {


	@JsonIgnore
	private int creditCardId;
	
	@NotNull
	@Size(max=40)
	private String name;

	@Pattern(regexp="(\\d{16})")
	@NotNull
	private String cardNumber;
	
	@Pattern(regexp="(\\d{3})")
	@NotNull
	private String cvv;

	@NotNull
	private String expirationDate;
	
	@NotNull
	private int userId;

}
