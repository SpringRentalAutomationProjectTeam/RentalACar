package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.dtos.CitySearchListDto;
import com.etiya.RentACar.business.requests.city.CreateCityRequest;
import com.etiya.RentACar.business.requests.city.DeleteCityRequest;
import com.etiya.RentACar.business.requests.city.UpdateCityRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.City;

import java.util.List;

public interface CityService {
    DataResult<List<CitySearchListDto>> getAll();
    Result add(CreateCityRequest createCityRequest);
    Result update(UpdateCityRequest updateCityRequest);
    Result delete(DeleteCityRequest deleteCityRequest);
    Result checkIfCityExists(int cityId);
    DataResult<City> getByCity(int cityId);
}
