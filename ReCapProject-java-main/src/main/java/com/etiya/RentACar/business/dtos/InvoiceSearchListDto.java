package com.etiya.RentACar.business.dtos;

import java.time.LocalDate;


import com.etiya.RentACar.entites.Rental;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceSearchListDto {

	private int rentalRentalId;

	private String invoiceNumber;
	
	private LocalDate invoiceDate;

	private int totalRentalDay;
	
	private double totalAmount;

}
