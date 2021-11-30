package com.etiya.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.CarImage;
import com.etiya.RentACar.entites.ComplexTypes.CarDetail;

public interface CarDao extends JpaRepository<Car, Integer> {

	
	/*@Query(value = "Select new com.etiya.RentACar.entites.ComplexTypes.CarDetail"
			+ "(c.id,b.brandName,cl.colorName,c.modelYear,c.dailyPrice,c.description) "
			+ "From Car c Inner Join c.brand b Inner Join c.color cl ", nativeQuery = true)
	List<CarDetail> getCarsWithBrandAndColorDetails();*/
		
	@Query("Select new com.etiya.RentACar.entites.ComplexTypes.CarDetail"
            + "(c.carId,b.brandName,cl.colorName,c.modelYear,c.dailyPrice,c.description) "
            + "From Car c Inner Join c.brand b Inner Join c.color cl")
    List<CarDetail> getCarWithBrandAndColorDetails();
	
	Car getById(int carId);
	boolean existsById(int carId);
	
	List<Car> getByColor_ColorId(int colorId);
	List<Car> getByBrand_BrandId(int brandId);
	
//	@Query("Select new com.etiya.RentACar.business.dtos.CarSearchListDto"
//			+ "(c.carId, c.modelYear, c.dailyPrice, c.description, c.minFindeksScore)"
//			+ "From Car c Inner Join c.maintenances m Where m.car.carId=c.id and m.returnDate is null "
//			)
//	List<CarSearchListDto> getCarsMaintenanceReturnDateIsNull();
	
//	select * from cars c left join   (select * from maintenance m  where m.return_date is null) a
//	on c.id=a.car_id where a.id  is null

	
	@Query(value="select c.carId, c.modelYear, c.dailyPrice, c.description, c.minFindeksScore "
			+ "from cars c left join "
			+ "(select * from maintenance m  where m.return_date is null) a "
			+ "on c.id=a.car_id where a.id  is null ",nativeQuery = true)
	List<CarSearchListDto> getCarsMaintenanceReturnDateIsNull();
	
	
	
}
