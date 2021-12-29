package com.etiya.RentACar.business.requests.IndıvidualCustomer;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	@Email
	private String email;
	@NotNull
	private String password;
	@NotNull
	private LocalDate birthday;
	
	@NotNull
	private int findeksScore;
}
