package com.etiya.RentACar.business.concretes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.BrandService;
import com.etiya.RentACar.business.abstracts.CarImageService;
import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.abstracts.ColorService;
import com.etiya.RentACar.business.abstracts.MaintenanceService;
import com.etiya.RentACar.business.abstracts.RentalService;
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

	@Autowired
	private CarManager(CarDao carDao, ModelMapperService modelMapperService,@Lazy CarImageService carImageService,
			@Lazy BrandService brandService, ColorService colorService, @Lazy RentalService rentalService, @Lazy MaintenanceService maintenanceService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.carImageService = carImageService;
		this.brandService = brandService;
		this.colorService = colorService;
		this.rentalService = rentalService;
		this.maintenanceService = maintenanceService;
		

	}

	@Override
	public DataResult<List<CarSearchListDto>> getAll() {
		List<Car> result = this.carDao.findAll();

		List<CarSearchListDto> response = result.stream()
				.map(car -> modelMapperService.forDto().map(car, CarSearchListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarSearchListDto>>(response, Messages.ListedCar);
	}

	@Override
	public DataResult<List<CarSearchListDto>> getAllAvailableCars() {
		List<Car> result = this.carDao.getCarWithoutCarMaintenance();
		List<CarSearchListDto> response =result.stream().map(car->modelMapperService.forDto()
				.map(car, CarSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarSearchListDto>>(response);
       
	}

	@Override
	public Result save(CreateCarRequest createCarRequest) {

		Result result = BusinessRules
				.run(checkBrandExists(createCarRequest.getBrandId()),checkColorExists(createCarRequest.getColorId()));

		if (result != null) {
			return result;
		}

		Car car = modelMapperService.forRequest().map(createCarRequest, Car.class);
		Random random = new Random();
		car.setMinFindeksScore(random.nextInt(1900));
		this.carDao.save(car);
		return new SuccessResult(Messages.AddedCar);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {// ıd den dolayı muhtemelen yeniden eklıyor
		Result result = BusinessRules.run(
				checkBrandExists(updateCarRequest.getBrandId())
				,checkColorExists(updateCarRequest.getColorId()),
				checkCarExists(updateCarRequest.getCarId()));

		if (result != null) {
			return result;
		}
		Car car = modelMapperService.forRequest().map(updateCarRequest, Car.class);
		car.setMinFindeksScore(this.carDao.getById(updateCarRequest.getCarId()).getMinFindeksScore());
		carDao.save(car);
		return new SuccessResult(Messages.UpdatedCar);
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {//kiradan dondu mu ve bakımda mı kontrolu yapılıcak brand ve color da bak
		Result result = BusinessRules.run(checkCarExists(deleteCarRequest.getCarId())
				,checkIfCarFromRental(deleteCarRequest.getCarId())
				,checkIfCarReturnMaintanence(deleteCarRequest.getCarId()));

		if (result != null) {
			return result;
		}
		Car car = modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		carDao.delete(car);
		return new SuccessResult();
	}

	@Override
	public DataResult<List<CarDetail>> getCarsWithBrandAndColorDetails() {
		List<CarDetail> result = this.carDao.getCarWithBrandAndColorDetails();
		return new SuccessDataResult<List<CarDetail>>(result);
	}

	@Override
	public DataResult<List<CarDetail>> getCarByBrandId(int brandId) {
		Result resultcheck = BusinessRules.run(checkBrandExists(brandId));

		if (resultcheck != null) {
			return new ErrorDataResult<List<CarDetail>>(null,"brand Bulunamadı");
		}
		List<Car> result = this.carDao.getByBrand_BrandId(brandId);
		List<CarDetail> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarDetail.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetail>>(response);
	}

	@Override
	public DataResult<List<CarDetail>> getCarByColorId(int colorId) {
		Result resultcheck = BusinessRules.run(checkColorExists(colorId));

		if (resultcheck != null) {
			return new ErrorDataResult<List<CarDetail>>(null,"Color Bulunamadı");
		}
		List<Car> result = this.carDao.getByColor_ColorId(colorId);
		List<CarDetail> response = result.stream().map(car -> modelMapperService.forDto().map(car, CarDetail.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarDetail>>(response);
	}

	@Override
	public DataResult<CarSearchListDto> getById(int carId) {

		Car car = this.carDao.findById(carId).get();

		if (car != null) {
			CarSearchListDto carSearchListDto = modelMapperService.forDto().map(car, CarSearchListDto.class);
			return new SuccessDataResult<CarSearchListDto>(carSearchListDto);
		}
		return null;
	}

	@Override
	public DataResult<List<CarDetailDto>> getByCarAllDetail(int carId) {
		Result result = BusinessRules.run(checkCarExists(carId));
		if (result != null) {
			return new ErrorDataResult<List<CarDetailDto>>(null, "Araba bulunamadı");
		}

		Car cars = this.carDao.getById(carId);
		List<CarDetailDto> carDetailDtos = new ArrayList<CarDetailDto>();
		CarDetailDto carDetailDto = modelMapperService.forDto().map(cars, CarDetailDto.class);
		carDetailDto.setCarImagesDetail(this.carImageService.getCarImageByCarId(cars.getCarId()).getData());
		carDetailDtos.add(carDetailDto);
		return new SuccessDataResult<List<CarDetailDto>>(carDetailDtos, "Araç detayları listelendi");

	}

	@Override
	public Result checkCarExists(int carId) {
		if (!this.carDao.existsById(carId)) {
			return new ErrorResult("araba bulunamadı");
		}
		return new SuccessResult();

	}
	

	private Result checkColorExists(int colorId) {
		if(!this.colorService.existsByColor_Id(colorId).isSuccess()) {
			return new ErrorResult("color bulunamadı");
		}
		return new SuccessResult();
	}
	
	private Result checkBrandExists(int brandId) {
		if (!this.brandService.existsByBrand_Id(brandId).isSuccess()) {
			return new ErrorResult("brand yada color bulunamdı");
		}
		return new SuccessResult();
	}

	private Result checkIfCarFromRental(int carId) {
		if (!this.rentalService.checkCarIsReturned(carId).isSuccess()) {
			return new ErrorResult("Araç kiradan dönmedi.");
		}
		return new SuccessResult();
	}
	
	private Result checkIfCarReturnMaintanence(int carId) {
		if (!this.maintenanceService.checkCarIsMaintenance(carId).isSuccess()) {
			return new ErrorResult("Araç bakımdan dönmedi.");
		}
		return new SuccessResult();
	}

	@Override
	public Result checkIfExistsColorIdInCar(int colorId) {
		if (!this.carDao.getByColor_ColorId(colorId).isEmpty()) {
			return new SuccessResult("Böyle bir color a sahip araba var");
		}
		return new ErrorResult();
	}

	@Override
	public Result checkIfExistsBrandIdInCar(int brandId) {
		if (!this.carDao.getByBrand_BrandId(brandId).isEmpty()) {
			return new SuccessResult("Böyle bir brand a sahip araba var");
		}
		return new ErrorResult();
		
	}
}
