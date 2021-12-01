package com.etiya.RentACar.entites;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int rentalId;
	
	@Column(name = "rent_date")
	private LocalDate rentDate;
	
	@Column(name ="return_date")
	private LocalDate returnDate;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(mappedBy = "rental")
	private Invoice invoice;


}
