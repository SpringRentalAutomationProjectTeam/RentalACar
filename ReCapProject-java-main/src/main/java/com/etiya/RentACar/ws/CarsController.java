package com.etiya.RentACar.ws;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.dtos.CarDetailDto;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.requests.car.CreateCarRequest;
import com.etiya.RentACar.business.requests.car.DeleteCarRequest;
import com.etiya.RentACar.business.requests.car.UpdateCarRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.ComplexTypes.CarDetail;

@RestController
@RequestMapping("api/cars/")
public class CarsController {

	private CarService carService;

	@Autowired
	private CarsController(CarService carService) {
		super();
		this.carService = carService;
	}

	@GetMapping("getAll")
	public DataResult<List<CarSearchListDto>> getAll() {
		return carService.getAll();
	}

	@GetMapping("getAllAvailableCars")
	public DataResult<List<CarSearchListDto>> getAllAvailableCars() {
		return carService.getAllAvailableCars();
	}

	@GetMapping("getCarsDetail")
	public DataResult<List<CarDetail>> getCarsWithBrandAndColorDetails() {
		return carService.getCarsWithBrandAndColorDetails();
	}
	@GetMapping("getCityId")
	public DataResult<List<CarSearchListDto>> getCarByCityId(int cityId){
		return carService.getCarByCityId(cityId);
	}

	@GetMapping("getBrandDetail")
	public DataResult<List<CarDetail>> getCarsWithBrandId(int brandId) {
		return carService.getCarByBrandId(brandId);
	}

	@GetMapping("getColorDetail")
	public DataResult<List<CarDetail>> getCarsWithColorId(int colorId) {
		return carService.getCarByColorId(colorId);
	}

	@GetMapping("getImageDetail")
	public DataResult<List<CarDetailDto>> getCarWithImageDetail(int carId) {
		return carService.getByCarAllDetail(carId);
	}

	@GetMapping("getbyId")
	public DataResult<CarSearchListDto> getByCarId(int carId) {
		return carService.getById(carId);
	}

	@PostMapping("add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}

	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}

	@DeleteMapping("delete")
	public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest) {
		return this.carService.delete(deleteCarRequest);
	}
}
