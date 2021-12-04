package com.etiya.RentACar.business.requests.corporateCustomers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.etiya.RentACar.business.constants.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	@NotNull
	private int userId;
	@NotNull
	private String companyName;
	@NotNull
	private String taxNumber;
	@Email(message = Messages.EMAILFORMATERROR)
	private String email;
	@NotNull
	private String password;


	
}
