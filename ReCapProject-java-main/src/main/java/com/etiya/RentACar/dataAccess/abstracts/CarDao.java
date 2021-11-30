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

	//çalışmıyor hocaya sor
	@Query(value = "Select c.id, c.model_year, c.daily_price, c.description, c.min_findeks_score "
			+ "from cars c left join "
			+ "(select * from maintenances m  where m.return_date is null) a"
			+ "on c.id=a.car_id where a.id is null ", nativeQuery = true)
	List<CarSearchListDto> getCarsMaintenanceReturnDateIsNull();

}
