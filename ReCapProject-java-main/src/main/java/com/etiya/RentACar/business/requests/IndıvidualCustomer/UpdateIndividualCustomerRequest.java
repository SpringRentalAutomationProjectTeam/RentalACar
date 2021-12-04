package com.etiya.RentACar.business.requests.IndıvidualCustomer;

import java.time.LocalDate;

import com.etiya.RentACar.business.constants.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	private int userId;
	private String firstName;
	private String lastName;

	@Email(message = Messages.EMAILFORMATERROR)
	private String email;
	private String password;
	private LocalDate birthday;
}
