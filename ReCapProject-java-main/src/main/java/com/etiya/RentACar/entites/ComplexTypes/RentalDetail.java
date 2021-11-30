package com.etiya.RentACar.entites.ComplexTypes;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDetail {
	private int id;
	//private int carId;
	private LocalDate rentDate;
	private LocalDate returnDate;
}
