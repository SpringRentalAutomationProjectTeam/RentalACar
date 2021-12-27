package com.etiya.RentACar.entites;

import java.time.LocalDate;
import java.util.List;

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

	@Column(name = "start_km")
	private String startKm;

	@Column(name = "end_Km")
	private String endKm;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(mappedBy = "rental")
	private Invoice invoice;

	@ManyToOne
	@JoinColumn(name = "rent_city_id")
	private City rentCity;

	@ManyToOne
	@JoinColumn(name = "return_city_id")
	private City returnCity;
    //car 1 brand rental bırden hızmetı

	@OneToMany(mappedBy = "rental")
	private List<AdditionalRentalItem>additionalRentalItems;





}
