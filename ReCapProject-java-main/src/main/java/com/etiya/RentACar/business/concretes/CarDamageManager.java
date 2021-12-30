package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.CarDamageService;
import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.CarDamageSearchListDto;
import com.etiya.RentACar.business.requests.carDamage.CreateCarDamageRequest;
import com.etiya.RentACar.business.requests.carDamage.DeleteCarDamageRequest;
import com.etiya.RentACar.business.requests.carDamage.UpdateCarDamageRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;
import com.etiya.RentACar.dataAccess.abstracts.CarDamageDao;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.CarDamage;
import com.etiya.RentACar.entites.City;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDamageManager implements CarDamageService {
    private ModelMapperService modelMapperService;
    private CarDamageDao carDamageDao;
    private CarService carService;
    private LanguageWordService languageWordService;

    @Autowired
    public CarDamageManager(ModelMapperService modelMapperService, CarDamageDao carDamageDao, CarService carService,LanguageWordService languageWordService) {
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.carDamageDao = carDamageDao;
        this.languageWordService= languageWordService;
    }

    @Override
    public DataResult<List<CarDamageSearchListDto>> getAllDamages() {
        List<CarDamage> list = this.carDamageDao.findAll();
        List<CarDamageSearchListDto> response = list.stream().map(carDamage -> modelMapperService.forDto().
                map(carDamage, CarDamageSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarDamageSearchListDto>>(response,
                this.languageWordService.getValueByKey(Messages.DAMAGELIST).getData());
    }

    @Override
    public DataResult<List<CarDamageSearchListDto>> getDamagesByCarId(int carId) {

        Result result = BusinessRules.run(checkIsThereDamageInCar(carId));

        if (result != null) {
            return new ErrorDataResult(result);
        }
        List<CarDamage> request = carDamageDao.getByCar_CarId(carId);
        List<CarDamageSearchListDto> response = request.stream().map(carDamage -> modelMapperService.forDto().map(carDamage, CarDamageSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarDamageSearchListDto>>(response);
    }

    @Override
    public Result add(CreateCarDamageRequest createCarDamageRequest) {
        Result result = BusinessRules.run(checkIfCarExists(createCarDamageRequest.getCarId()));
        if (result != null) {
            return result;
        }

        CarDamage carDamage = modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
        this.carDamageDao.save(carDamage);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.DAMAGEADD).getData());
    }

    @Override
    public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
        Result result = BusinessRules.run(checkIfCarExists(updateCarDamageRequest.getCarId()),
                checkIfCarDamageExists(updateCarDamageRequest.getCarDamageId()));
        if (result != null) {
            return result;
        }

        CarDamage carDamage = modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
        this.carDamageDao.save(carDamage);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.DAMAGEUPDATE).getData());
    }

    @Override
    public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) {
        Result result = BusinessRules.run(checkIfCarDamageExists(deleteCarDamageRequest.getCarDamageId()));
        if (result != null) {
            return result;
        }

        CarDamage carDamage = modelMapperService.forRequest().map(deleteCarDamageRequest, CarDamage.class);
        this.carDamageDao.delete(carDamage);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.DAMAGEDELETE).getData());
    }

    private Result checkIfCarDamageExists(int carDamageId){
        if (!this.carDamageDao.existsById(carDamageId)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.DAMAGENOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIsThereDamageInCar(int carId){
        if (this.carDamageDao.getByCar_CarId(carId).isEmpty()){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.DAMAGEBELONGTOCAR).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCarExists(int carId) {
        if (!this.carService.checkIfCarExists(carId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.DAMAGENOTFOUND).getData());
        }
        return new SuccessResult();
    }
}
