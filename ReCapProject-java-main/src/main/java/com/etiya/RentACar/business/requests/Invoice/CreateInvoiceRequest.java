package com.etiya.RentACar.business.requests.Invoice;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.etiya.RentACar.entites.Rental;
import com.etiya.RentACar.entites.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	
   private int invoiceId;
	
	private int invoiceNumber;
	
	private int userId;
	
	
	

}
