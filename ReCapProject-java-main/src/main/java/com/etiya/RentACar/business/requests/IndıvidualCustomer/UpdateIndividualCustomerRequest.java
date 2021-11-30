package com.etiya.RentACar.business.requests.IndıvidualCustomer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private LocalDate birthday;
}
