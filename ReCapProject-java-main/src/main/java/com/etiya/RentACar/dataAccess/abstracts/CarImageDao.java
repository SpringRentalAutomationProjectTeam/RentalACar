package com.etiya.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.business.dtos.CarImagesDto;
import com.etiya.RentACar.business.dtos.CarImagesSearchListDto;
import com.etiya.RentACar.entites.CarImage;

public interface CarImageDao extends JpaRepository<CarImage, Integer>{
	
	int countCarImageByCar_CarId(int carId);
	
	List<CarImage> getByCar_CarId(int carId);
	
	boolean existsByCar_CarId(int carId);
	
}
