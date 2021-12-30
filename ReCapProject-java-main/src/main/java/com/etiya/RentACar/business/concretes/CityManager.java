package com.etiya.RentACar.business.concretes;


import com.etiya.RentACar.business.abstracts.CityService;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.constants.Messages;
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
    private LanguageWordService languageWordService;

    @Autowired
    public CityManager(ModelMapperService modelMapperService, CityDao cityDao
    ,LanguageWordService languageWordService) {
        this.modelMapperService = modelMapperService;
        this.cityDao = cityDao;
        this.languageWordService=languageWordService;
    }

    @Override
    public DataResult<List<CitySearchListDto>> getAll() {
        List<City> cities = this.cityDao.findAll();
        List<CitySearchListDto> response = cities.stream().map(city -> modelMapperService.forDto()
                .map(city, CitySearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CitySearchListDto>>(response, this.languageWordService.getValueByKey(Messages.CITYLIST).getData());
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {
        Result result = BusinessRules.run(checkIfCityNameExists(createCityRequest.getCityName()));
        if (result != null) {
            return result;
        }

        City city = modelMapperService.forRequest().map(createCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CITYADD).getData());
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {
        Result result = BusinessRules.run(checkIfCityExists(updateCityRequest.getCityId()),
                checkIfCityNameExists(updateCityRequest.getCityName()));
        if (result != null) {
            return result;
        }

        City city = modelMapperService.forRequest().map(updateCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CITYUPDATE).getData());
    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) {
        Result result = BusinessRules.run(checkIfCityExists(deleteCityRequest.getCityId()));
        if (result != null) {
            return result;
        }

        this.cityDao.deleteById(deleteCityRequest.getCityId());
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CITYDELETE).getData());
    }

    @Override
    public DataResult<City> getByCity(int cityId) {
        Result result = BusinessRules.run(checkIfCityExists(cityId));
        if (result != null) {
            return new ErrorDataResult(result);
        }

        return new SuccessDataResult<City>(this.cityDao.getById(cityId));
    }

    @Override
    public Result checkIfCityExists(int cityId) {
        if (!this.cityDao.existsById(cityId)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CITYNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCityNameExists(String cityName) {
        if (this.cityDao.existsByCityName(cityName)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CITYALREADYEXISTS).getData());
        }
        return new SuccessResult();
    }

}
