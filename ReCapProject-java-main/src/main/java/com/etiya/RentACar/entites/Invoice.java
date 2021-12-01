package com.etiya.RentACar.entites;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="invoices")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="invoice_id")
	private int invoiceId;
	
	@Column(name="invoice_number")
	private String invoiceNumber;
	
	@Column(name="invoice_date")
	private LocalDate invoiceDate;
	
	@Column(name="total_rental_day")
	private int totalRentalDay;
	
	@Column(name="total_amount")
	private double totalAmount;


	@OneToOne
	@JoinColumn(name="rental_id")
	private Rental rental;
	
	
}