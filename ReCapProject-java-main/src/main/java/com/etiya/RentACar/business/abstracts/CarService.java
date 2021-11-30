package com.etiya.RentACar.business.abstracts;

import java.util.List;

import com.etiya.RentACar.business.dtos.CarDetailDto;
import com.etiya.RentACar.business.dtos.CarImagesDto;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.requests.car.CreateCarRequest;
import com.etiya.RentACar.business.requests.car.DeleteCarRequest;
import com.etiya.RentACar.business.requests.car.UpdateCarRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.ComplexTypes.CarDetail;
import com.etiya.RentACar.entites.ComplexTypes.CarImageDetail;

public interface CarService {
	DataResult<List<CarSearchListDto>> getAll();

	DataResult<List<CarSearchListDto>> getAllAvailableCars();
	
	Result save(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	Result delete(DeleteCarRequest deleteCarRequest);

	DataResult<List<CarDetail>> getCarsWithBrandAndColorDetails();
	
	DataResult<List<CarDetail>> getCarByBrandId(int brandId);
	DataResult<List<CarDetail>> getCarByColorId(int colorId);
	
	DataResult<CarSearchListDto> getById(int id);
	DataResult<List<CarDetailDto>> getByCarAllDetail(int carId);
	Result checkCarExists(int carId);
	
	Result checkIfExistsColorIdInCar(int colorId);
	Result checkIfExistsBrandIdInCar(int brandId);
	
}
