package com.etiya.RentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {

	private int creditCardId;
	
	private String name;

	private String cardNumber;
	
	private String cvv;
	
	private int individualCustomerId;
}
