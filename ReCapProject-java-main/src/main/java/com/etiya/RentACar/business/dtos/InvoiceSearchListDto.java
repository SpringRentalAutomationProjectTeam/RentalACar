package com.etiya.RentACar.business.dtos;

import java.time.LocalDate;


import com.etiya.RentACar.entites.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceSearchListDto {

	private int invoiceId;
	
	private int invoiceNumber;
	
	private LocalDate invoiceDate;
	
	private int totalRentalDay;
	
	private double totalAmount;

	private int rentalId;
	
}
