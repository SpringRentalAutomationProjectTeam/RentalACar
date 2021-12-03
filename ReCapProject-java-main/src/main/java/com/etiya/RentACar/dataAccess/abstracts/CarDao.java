package com.etiya.RentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.CarImage;
import com.etiya.RentACar.entites.ComplexTypes.CarDetail;

public interface CarDao extends JpaRepository<Car, Integer> {

	@Query("Select new com.etiya.RentACar.entites.ComplexTypes.CarDetail"
			+ "(c.carId,b.brandName,cl.colorName,c.modelYear,c.dailyPrice,c.description) "
			+ "From Car c Inner Join c.brand b Inner Join c.color cl")
	List<CarDetail> getCarWithBrandAndColorDetails();

	Car getById(int carId);

	boolean existsById(int carId);

	List<Car> getByColor_ColorId(int colorId);

	List<Car> getByBrand_BrandId(int brandId);
	List<Car> getByCity_CityId(int cityId);


	@Query("Select new com.etiya.RentACar.business.dtos.CarSearchListDto"
			+ "(c.carId,c.modelYear,c.dailyPrice,c.description,c.minFindeksScore,c.city.cityName) "
			+ "From Car c Left Join  c.maintenances cm WHERE " +
			"(cm.maintenanceDate is not null AND cm.returnDate is not null) or (cm.maintenanceDate is null AND cm.returnDate is null)")
	List<CarSearchListDto> getAllWithoutMaintenanceOfCar();

}
