package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.RentACar.business.dtos.MaintenanceDto;
import com.etiya.RentACar.business.dtos.RentalSearchListDto;
import com.etiya.RentACar.entites.Rental;
import com.etiya.RentACar.entites.ComplexTypes.RentalDetail;;

public interface RentalDao extends JpaRepository<Rental, Integer>{
	@Query("Select new com.etiya.RentACar.business.dtos.RentalSearchListDto"
			+ "(c.id, r.returnDate) " 
			+ 	"From Car c Inner Join c.rentals r where c.id=:carId and r.returnDate is null")
	RentalSearchListDto getByCarIdWhereReturnDateIsNull(int carId);
	
	@Query("Select new com.etiya.RentACar.entites.ComplexTypes.RentalDetail"
			+"(r.id,r.rentDate,r.returnDate)"
			+"From Rental r Inner Join r.car c Where r.id=:rentalId")
	 RentalDetail getRentalDetails(int rentalId);
	

	boolean existsById(int rentalId);
	@Query("Select new com.etiya.RentACar.business.dtos.MaintenanceDto"
			+ "(c.id, m.returnDate) " 
			+ 	"From Car c Inner Join c.maintenances m Where c.id=:carId and m.returnDate is null")
	MaintenanceDto getByCarIdWhereMaintenanceReturnDateIsNull(int carId);
	
}
