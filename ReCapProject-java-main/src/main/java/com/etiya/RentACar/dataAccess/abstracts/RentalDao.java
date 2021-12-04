package com.etiya.RentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.RentACar.business.dtos.MaintenanceDto;
import com.etiya.RentACar.business.dtos.RentalSearchListDto;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.entites.Rental;
import com.etiya.RentACar.entites.ComplexTypes.RentalDetail;;

public interface RentalDao extends JpaRepository<Rental, Integer>{
	boolean existsById(int rentalId);
  
	@Query("Select new com.etiya.RentACar.entites.ComplexTypes.RentalDetail"
			+ "(c.carId, r.returnDate) " 
			+ 	"From Car c Inner Join c.rentals r where c.carId=:carId and r.returnDate is null")
	RentalDetail getByCarIdWhereReturnDateIsNull(int carId);

	@Query("Select new com.etiya.RentACar.business.dtos.MaintenanceDto"
			+ "(c.carId, m.returnDate) " 
			+ 	"From Car c Inner Join c.maintenances m Where c.carId=:carId and m.returnDate is null")
	MaintenanceDto getByCarIdWhereMaintenanceReturnDateIsNull(int carId);
	
}
