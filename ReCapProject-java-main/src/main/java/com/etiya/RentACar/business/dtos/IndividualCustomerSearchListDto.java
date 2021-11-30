package com.etiya.RentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerSearchListDto {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate birthday;
}
