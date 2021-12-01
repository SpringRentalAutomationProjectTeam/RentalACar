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

	/*
	 * @Query("Select new com.etiya.RentACar.business.dtos.CarSearchListDto " +
	 * "(c.carId , c.modelYear , c.dailyPrice , c.description , c.minFindeksScore) "
	 * +"from Car c Left Join Maintenance m on"
	 * +"m.car.carId=c.carId where m.returnDate is null and m.rentDate is null")
	 * List<CarSearchListDto> getCarsMaintenanceReturnDateIsNull();
	 */
	/*
	 * query toolda calısan sorgu select c.id ,
	 * c.model_year,c.daily_price,c.description,c.min_findeks_score from cars c left
	 * join maintenances m on m.car_id=c.id where m.return_date is null and
	 * rent_date is null
	 */


	@Query("Select new com.etiya.RentACar.business.dtos.CarSearchListDto" + "(c.carId,c.modelYear,c.dailyPrice,c.description,c.minFindeksScore) "
			+ "From Car c Left Join  c.maintenances cm WHERE (cm.maintenanceDate is not null AND cm.returnDate is not null) or (cm.maintenanceDate is null AND cm.returnDate is null)")
	List<CarSearchListDto> getAllWithoutMaintenanceOfCar();

	//ben yapıyorum
	@Query(value = "select c.id,c.daily_price,c.description,c.min_findeks_score,c.model_year,c.brand_id,c.color_id "
			+ "from cars c left join maintenances cm "
			+ "on c.id=cm.car_id  "
			+ "where cm.return_date  is null", nativeQuery = true)
	List<Car> getCarWithoutCarMaintenance();

}
