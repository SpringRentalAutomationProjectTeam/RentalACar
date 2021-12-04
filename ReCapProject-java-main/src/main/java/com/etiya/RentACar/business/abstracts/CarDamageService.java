package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.dtos.CarDamageSearchListDto;
import com.etiya.RentACar.business.requests.carDamage.CreateCarDamageRequest;
import com.etiya.RentACar.business.requests.carDamage.DeleteCarDamageRequest;
import com.etiya.RentACar.business.requests.carDamage.UpdateCarDamageRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

import java.util.List;

public interface CarDamageService {
    DataResult<List<CarDamageSearchListDto>> getAllDamages();
    Result add(CreateCarDamageRequest createCarDamageRequest);
    Result delete(DeleteCarDamageRequest deleteCarDamageRequest);
    Result update(UpdateCarDamageRequest updateCarDamageRequest);
    DataResult<List<CarDamageSearchListDto>> getDamagesByCarId(int carId);
}
