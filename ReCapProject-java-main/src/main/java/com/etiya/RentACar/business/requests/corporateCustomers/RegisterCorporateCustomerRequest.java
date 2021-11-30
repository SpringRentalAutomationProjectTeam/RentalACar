package com.etiya.RentACar.business.requests.corporateCustomers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCorporateCustomerRequest {

	@NotNull
	private String companyName;
	@NotNull
	private String taxNumber;
	@Email
	private String email;
	@NotNull
	private String password;

}
