package com.etiya.RentACar.business.concretes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.etiya.RentACar.business.abstracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.constants.FilePathConfiguration;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.CarDetailDto;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.requests.car.CreateCarRequest;
import com.etiya.RentACar.business.requests.car.DeleteCarRequest;
import com.etiya.RentACar.business.requests.car.UpdateCarRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorDataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.CarDao;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.CarImage;
import com.etiya.RentACar.entites.ComplexTypes.CarDetail;

@Service
public class CarManager implements CarService {

    private CarDao carDao;
    private ModelMapperService modelMapperService;
    private CarImageService carImageService;
    private BrandService brandService;
    private ColorService colorService;
    private RentalService rentalService;
    private MaintenanceService maintenanceService;
    private CityService cityService;
    private LanguageWordService languageWordService;

    @Autowired
    private CarManager(CarDao carDao, ModelMapperService modelMapperService, @Lazy CarImageService carImageService,
                       @Lazy BrandService brandService, ColorService colorService, @Lazy RentalService rentalService,
                       @Lazy MaintenanceService maintenanceService, CityService cityService,LanguageWordService languageWordService) {

        this.carDao = carDao;
        this.modelMapperService = modelMapperService;
        this.carImageService = carImageService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.rentalService = rentalService;
        this.cityService = cityService;
        this.maintenanceService = maintenanceService;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<CarSearchListDto>> getAll() {
        List<Car> result = this.carDao.findAll();
        List<CarSearchListDto> response = result.stream()
                .map(car -> modelMapperService.forDto().map(car, CarSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarSearchListDto>>(response, this.languageWordService.getValueByKey("car_list").getData());
    }

    @Override
    public DataResult<List<CarSearchListDto>> getAllAvailableCars() {
        List<CarSearchListDto> result = this.carDao.getAllWithoutMaintenanceOfCar();
        return new SuccessDataResult<List<CarSearchListDto>>(result,this.languageWordService.getValueByKey("car_available").getData());
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) {
        Result result = BusinessRules
                .run(checkIfCityExists(createCarRequest.getCityId()),
                        checkIfBrandExists(createCarRequest.getBrandId()), checkIfColorExists(createCarRequest.getColorId()));
        if (result != null) {
            return result;
        }

        Car car = modelMapperService.forRequest().map(createCarRequest, Car.class);
        Random random = new Random();
        car.setMinFindeksScore(random.nextInt(1900));
        this.carDao.save(car);
        return new SuccessResult(this.languageWordService.getValueByKey("car_add").getData());
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) {
        Result result = BusinessRules.run(checkIfCityExists(updateCarRequest.getCityId()),
                checkIfBrandExists(updateCarRequest.getBrandId())
                , checkIfColorExists(updateCarRequest.getColorId()),
                checkIfCarExists(updateCarRequest.getCarId()));
        if (result != null) {
            return result;
        }

        Car car = modelMapperService.forRequest().map(updateCarRequest, Car.class);
        car.setMinFindeksScore(this.carDao.getById(updateCarRequest.getCarId()).getMinFindeksScore());
        carDao.save(car);
        return new SuccessResult(this.languageWordService.getValueByKey("car_update").getData());
    }

    @Override
    public Result delete(DeleteCarRequest deleteCarRequest) {
        Result result = BusinessRules.run(checkIfCarExists(deleteCarRequest.getCarId())
                , checkIfCarReturnRental(deleteCarRequest.getCarId())
                , checkIfCarReturnMaintenance(deleteCarRequest.getCarId()));
        if (result != null) {
            return result;
        }

        Car car = modelMapperService.forRequest().map(deleteCarRequest, Car.class);
        carDao.delete(car);
        return new SuccessResult(this.languageWordService.getValueByKey("car_update").getData());
    }

    @Override
    public DataResult<List<CarDetail>> getCarsWithBrandAndColorDetails() {
        List<CarDetail> result = this.carDao.getCarWithBrandAndColorDetails();
        return new SuccessDataResult<List<CarDetail>>(result,this.languageWordService.getValueByKey("car_brand_and_color_list").getData());
    }

    @Override
    public DataResult<List<CarDetail>> getCarByBrandId(int brandId) {
        Result resultcheck = BusinessRules.run(checkIfBrandExists(brandId));

        if (resultcheck != null) {
            return new ErrorDataResult<List<CarDetail>>(null,this.languageWordService.getValueByKey("brand_not_found").getData());
        }
        List<Car> result = this.carDao.getByBrand_BrandId(brandId);
        List<CarDetail> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarDetail.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarDetail>>(response,this.languageWordService.getValueByKey("car_get_brand").getData());
    }

    @Override
    public DataResult<List<CarDetail>> getCarByColorId(int colorId) {
        Result resultcheck = BusinessRules.run(checkIfColorExists(colorId));

        if (resultcheck != null) {
            return new ErrorDataResult<List<CarDetail>>(null,this.languageWordService.getValueByKey("color_not_found").getData());
        }
        List<Car> result = this.carDao.getByColor_ColorId(colorId);
        List<CarDetail> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarDetail.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarDetail>>(response,this.languageWordService.getValueByKey("car_get_color").getData());
    }

    @Override
    public DataResult<CarSearchListDto> getById(int carId) {

        Result result = BusinessRules.run(checkIfCarExists(carId));
        if (result!=null){
            return new ErrorDataResult(result);
        }

        Car car = this.carDao.findById(carId).get();

            CarSearchListDto carSearchListDto = modelMapperService.forDto().map(car, CarSearchListDto.class);
            return new SuccessDataResult<CarSearchListDto>(carSearchListDto,this.languageWordService.getValueByKey("car_found").getData());

    }

    @Override
    public DataResult<List<CarDetailDto>> getByCarAllDetail(int carId) {
        Result result = BusinessRules.run(checkIfCarExists(carId));
        if (result != null) {
            return new ErrorDataResult<List<CarDetailDto>>(null,this.languageWordService.getValueByKey("car_not_found").getData());
        }

        Car cars = this.carDao.getById(carId);
        List<CarDetailDto> carDetailDtos = new ArrayList<CarDetailDto>();
        CarDetailDto carDetailDto = modelMapperService.forDto().map(cars, CarDetailDto.class);
        carDetailDto.setCarImagesDetail(this.carImageService.getCarImageByCarId(cars.getCarId()).getData());
        carDetailDtos.add(carDetailDto);
        return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, this.languageWordService.getValueByKey("car_list").getData());
    }

    @Override
    public DataResult<List<CarSearchListDto>> getCarByCityId(int cityId) {

        Result resultCheck = BusinessRules.run(checkIfCityExists(cityId));
        if (resultCheck!=null){
            return  new ErrorDataResult(resultCheck);
        }

        List<Car> result = this.carDao.getByCity_CityId(cityId);
        List<CarSearchListDto> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarSearchListDto>>(response,this.languageWordService.getValueByKey("car_get_city").getData());
    }

    @Override
    public Result updateCarCity(int carId, int cityId) {

        Car request = this.carDao.getById(carId);
        request.setCity(this.cityService.getByCity(cityId).getData());
        this.carDao.save(request);
        return new SuccessResult(this.languageWordService.getValueByKey("city_update").getData());
    }

    @Override
    public Result updateCarKm(int carId, String kilometer) {
        Car car = this.carDao.getById(carId);
        car.setKilometer(kilometer);
        this.carDao.save(car);
        return new SuccessResult(this.languageWordService.getValueByKey("car_km_update").getData());
    }

    @Override
    public Result checkIfCarExists(int carId) {
        if (!this.carDao.existsById(carId)) {
            return new ErrorResult(this.languageWordService.getValueByKey("car_not_found").getData());
        }
        return new SuccessResult();
    }

    @Override
    public Result checkIfExistsColorInCar(int colorId) {
        if (!this.carDao.getByColor_ColorId(colorId).isEmpty()) {
            return new SuccessResult(this.languageWordService.getValueByKey("color_delete_error").getData());
        }
        return new ErrorResult();
    }

    @Override
    public Result checkIfExistsBrandInCar(int brandId) {
        if (!this.carDao.getByBrand_BrandId(brandId).isEmpty()) {
            return new SuccessResult(this.languageWordService.getValueByKey("brand_delete_error").getData());
        }
        return new ErrorResult();
    }


    private Result checkIfColorExists(int colorId) {
        if (!this.colorService.checkIfColorExists(colorId).isSuccess()) {
            return new SuccessResult(this.languageWordService.getValueByKey("color_not_found").getData());
        }
        return new SuccessResult();
    }

    private Result checkIfBrandExists(int brandId) {
        if (!this.brandService.checkIfBrandExists(brandId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey("brand_not_found").getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCarReturnRental(int carId) {
        if (!this.rentalService.checkIfCarIsReturned(carId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey("car_is_on_rent").getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCarReturnMaintenance(int carId) {
        if (!this.maintenanceService.checkIfCarIsMaintenance(carId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey("carmaintenance_found").getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCityExists(int cityId){
        if (!this.cityService.getByCity(cityId).isSuccess()){
            return new ErrorResult(this.languageWordService.getValueByKey("city_not_found").getData());
        }
        return new SuccessResult();
    }

}
