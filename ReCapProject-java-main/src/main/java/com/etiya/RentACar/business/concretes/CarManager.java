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

    @Autowired
    private CarManager(CarDao carDao, ModelMapperService modelMapperService, @Lazy CarImageService carImageService,
                       @Lazy BrandService brandService, ColorService colorService, @Lazy RentalService rentalService,
                       @Lazy MaintenanceService maintenanceService, CityService cityService) {
        super();
        this.carDao = carDao;
        this.modelMapperService = modelMapperService;
        this.carImageService = carImageService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.rentalService = rentalService;
        this.cityService = cityService;
        this.maintenanceService = maintenanceService;
    }

    @Override
    public DataResult<List<CarSearchListDto>> getAll() {
        List<Car> result = this.carDao.findAll();
        List<CarSearchListDto> response = result.stream()
                .map(car -> modelMapperService.forDto().map(car, CarSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarSearchListDto>>(response, Messages.CARLIST);
    }

    @Override
    public DataResult<List<CarSearchListDto>> getAllAvailableCars() {
        List<CarSearchListDto> result = this.carDao.getAllWithoutMaintenanceOfCar();
        return new SuccessDataResult<List<CarSearchListDto>>(result);
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) {
        Result result = BusinessRules
                .run(checkIfBrandExists(createCarRequest.getBrandId()), checkIfColorExists(createCarRequest.getColorId()));
        if (result != null) {
            return result;
        }

        Car car = modelMapperService.forRequest().map(createCarRequest, Car.class);
        Random random = new Random();
        car.setMinFindeksScore(random.nextInt(1900));
        this.carDao.save(car);
        return new SuccessResult(Messages.CARADD);
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) {
        Result result = BusinessRules.run(
                checkIfBrandExists(updateCarRequest.getBrandId())
                , checkIfColorExists(updateCarRequest.getColorId()),
                checkIfCarExists(updateCarRequest.getCarId()));
        if (result != null) {
            return result;
        }

        Car car = modelMapperService.forRequest().map(updateCarRequest, Car.class);
        car.setMinFindeksScore(this.carDao.getById(updateCarRequest.getCarId()).getMinFindeksScore());
        carDao.save(car);
        return new SuccessResult(Messages.CARUPDATE);
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
        return new SuccessResult(Messages.CARDELETE);
    }

    @Override
    public DataResult<List<CarDetail>> getCarsWithBrandAndColorDetails() {
        List<CarDetail> result = this.carDao.getCarWithBrandAndColorDetails();
        return new SuccessDataResult<List<CarDetail>>(result,Messages.CARBRANDANDCOLORLIST);
    }

    @Override
    public DataResult<List<CarDetail>> getCarByBrandId(int brandId) {
        Result resultcheck = BusinessRules.run(checkIfBrandExists(brandId));

        if (resultcheck != null) {
            return new ErrorDataResult<List<CarDetail>>(null,Messages.BRANDNOTFOUND);
        }
        List<Car> result = this.carDao.getByBrand_BrandId(brandId);
        List<CarDetail> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarDetail.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarDetail>>(response,Messages.CARGETBRAND);
    }

    @Override
    public DataResult<List<CarDetail>> getCarByColorId(int colorId) {
        Result resultcheck = BusinessRules.run(checkIfColorExists(colorId));

        if (resultcheck != null) {
            return new ErrorDataResult<List<CarDetail>>(null,Messages.COLORNOTFOUND);
        }
        List<Car> result = this.carDao.getByColor_ColorId(colorId);
        List<CarDetail> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarDetail.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarDetail>>(response,Messages.CARGETCOLOR);
    }

    @Override
    public DataResult<CarSearchListDto> getById(int carId) {
        Car car = this.carDao.findById(carId).get();
        if (car != null) {
            CarSearchListDto carSearchListDto = modelMapperService.forDto().map(car, CarSearchListDto.class);
            return new SuccessDataResult<CarSearchListDto>(carSearchListDto,Messages.CARGET);
        }
        return null;
    }

    @Override
    public DataResult<List<CarDetailDto>> getByCarAllDetail(int carId) {
        Result result = BusinessRules.run(checkIfCarExists(carId));
        if (result != null) {
            return new ErrorDataResult<List<CarDetailDto>>(null,Messages.CARNOTFOUND);
        }

        Car cars = this.carDao.getById(carId);
        List<CarDetailDto> carDetailDtos = new ArrayList<CarDetailDto>();
        CarDetailDto carDetailDto = modelMapperService.forDto().map(cars, CarDetailDto.class);
        carDetailDto.setCarImagesDetail(this.carImageService.getCarImageByCarId(cars.getCarId()).getData());
        carDetailDtos.add(carDetailDto);
        return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, Messages.CARLIST);
    }

    @Override
    public DataResult<List<CarSearchListDto>> getCarByCityId(int cityId) {
        List<Car> result = this.carDao.getByCity_CityId(cityId);
        List<CarSearchListDto> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarSearchListDto>>(response,Messages.CARGETCITY);
    }

    @Override
    public Result updateCarCity(int carId, int cityId) {
        Car request = this.carDao.getById(carId);
        request.setCity(this.cityService.getByCity(cityId).getData());
        this.carDao.save(request);
        return new SuccessResult(Messages.CITYUPDATE);
    }

    @Override
    public Result updateCarKm(int carId, String kilometer) {
        Car car = this.carDao.getById(carId);
        car.setKilometer(kilometer);
        this.carDao.save(car);
        return new SuccessResult(Messages.CARKMUPDATE);
    }

    @Override
    public Result checkIfCarExists(int carId) {
        if (!this.carDao.existsById(carId)) {
            return new ErrorResult(Messages.CARNOTFOUND);
        }
        return new SuccessResult(Messages.CARFOUND);
    }

    @Override
    public Result checkIfExistsColorInCar(int colorId) {
        if (!this.carDao.getByColor_ColorId(colorId).isEmpty()) {
            return new SuccessResult(Messages.COLORDELETEERROR);
        }
        return new ErrorResult();
    }

    @Override
    public Result checkIfExistsBrandInCar(int brandId) {
        if (!this.carDao.getByBrand_BrandId(brandId).isEmpty()) {
            return new SuccessResult(Messages.BRANDDELETEERROR);
        }
        return new ErrorResult();
    }


    private Result checkIfColorExists(int colorId) {
        if (!this.colorService.checkIfColorExists(colorId).isSuccess()) {
            return new ErrorResult(Messages.COLORNOTFOUND);
        }
        return new SuccessResult();
    }

    private Result checkIfBrandExists(int brandId) {
        if (!this.brandService.checkIfBrandExists(brandId).isSuccess()) {
            return new ErrorResult(Messages.BRANDNOTFOUND);
        }
        return new SuccessResult();
    }

    private Result checkIfCarReturnRental(int carId) {
        if (!this.rentalService.checkIfCarIsReturned(carId).isSuccess()) {
            return new ErrorResult(Messages.CARDONOTRETURNFROMRENTAL);
        }
        return new SuccessResult();
    }

    private Result checkIfCarReturnMaintenance(int carId) {
        if (!this.maintenanceService.checkIfCarIsMaintenance(carId).isSuccess()) {
            return new ErrorResult(Messages.CARMAINTENANCEFOUND);
        }
        return new SuccessResult();
    }

}
