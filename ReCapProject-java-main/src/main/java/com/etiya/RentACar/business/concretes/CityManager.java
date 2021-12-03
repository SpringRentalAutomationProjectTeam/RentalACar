package com.etiya.RentACar.business.concretes;


import com.etiya.RentACar.business.abstracts.CityService;
import com.etiya.RentACar.business.dtos.CitySearchListDto;
import com.etiya.RentACar.business.requests.city.CreateCityRequest;
import com.etiya.RentACar.business.requests.city.DeleteCityRequest;
import com.etiya.RentACar.business.requests.city.UpdateCityRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;
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
    public CityManager(ModelMapperService modelMapperService, CityDao cityDao) {
        this.modelMapperService = modelMapperService;
        this.cityDao = cityDao;
    }

    @Override
    public DataResult<List<CitySearchListDto>> getAll() {
        List<City> cities = this.cityDao.findAll();
        List<CitySearchListDto> response = cities.stream().map(city -> modelMapperService.forDto()
                .map(city, CitySearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CitySearchListDto>>(response);
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {
        Result result= BusinessRules.run(existsByCityName(createCityRequest.getCityName()));
        if (result!=null){
            return result;
        }
        City city = modelMapperService.forRequest().map(createCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult("City added");
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {
        Result result= BusinessRules.run(existsByCityName(updateCityRequest.getCityName()));
        if (result!=null){
            return result;
        }
        City city = modelMapperService.forRequest().map(updateCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult("City updated");
    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) {
        Result result= BusinessRules.run(existsByCityId(deleteCityRequest.getCityId()));
        if (result!=null){
            return result;
        }
        this.cityDao.deleteById(deleteCityRequest.getCityId());
        return new SuccessResult("silme işlemi gerçekleşti");
    }


    @Override
    public Result existsByCityId(int cityId) {
        if (!this.cityDao.existsById(cityId)) {
            return new ErrorResult("City Bulunamadı");
        }
        return new SuccessResult();
    }

    @Override
    public DataResult<City> getByCity(int cityId) {
        return new SuccessDataResult<City>( this.cityDao.getById(cityId));
    }

    private Result existsByCityName(String cityName) {
        if (this.cityDao.existsByCityName(cityName)) {
            return new ErrorResult("City ismi bulundu başka city giriniz");
        }
        return new SuccessResult();
    }

}
