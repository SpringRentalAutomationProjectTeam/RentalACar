package com.etiya.RentACar.business.concretes;


import com.etiya.RentACar.business.abstracts.CityService;
import com.etiya.RentACar.business.dtos.CitySearchListDto;
import com.etiya.RentACar.business.requests.city.CreateCityRequest;
import com.etiya.RentACar.business.requests.city.DeleteCityRequest;
import com.etiya.RentACar.business.requests.city.UpdateCityRequest;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.CityDao;
import com.etiya.RentACar.entites.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CityManager implements CityService {

    private CityDao cityDao;

    private ModelMapperService modelMapperService;

    @Autowired
    public CityManager(ModelMapperService modelMapperService,CityDao cityDao) {
        this.modelMapperService = modelMapperService;
        this.cityDao=cityDao;
    }

    @Override
    public DataResult<List<CitySearchListDto>> getAll() {
       List<City> cities = this.cityDao.findAll();
       List<CitySearchListDto> response = cities.stream().map(city-> modelMapperService.forDto()
               .map(city , CitySearchListDto.class)).collect(Collectors.toList());
       return new SuccessDataResult<List<CitySearchListDto>>(response);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {
        City city = modelMapperService.forRequest().map(createCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult("City added");
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {
        City city = modelMapperService.forRequest().map(updateCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult("City updated");
    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) {
        this.cityDao.deleteById(deleteCityRequest.getCityId());
        return new SuccessResult("silme işlemi gerçekleşti");
    }
}
