package com.etiya.RentACar.business.requests.creditCard;

import javax.validation.constraints.NotNull;

import com.etiya.RentACar.business.requests.LoginRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCreditCardRequest {
	
	@NotNull
	private int creditCardId;
}
