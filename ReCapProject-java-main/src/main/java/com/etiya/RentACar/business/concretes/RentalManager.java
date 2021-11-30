package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.abstracts.MaintenanceService;
import com.etiya.RentACar.business.abstracts.RentalService;
import com.etiya.RentACar.business.abstracts.UserService;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.dtos.MaintenanceDto;
import com.etiya.RentACar.business.dtos.RentalSearchListDto;
import com.etiya.RentACar.business.requests.Rental.CreateRentalRequest;
import com.etiya.RentACar.business.requests.Rental.DeleteRentalRequest;
import com.etiya.RentACar.business.requests.Rental.UpdateRentalRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.RentalDao;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.IndividualCustomer;
import com.etiya.RentACar.entites.Maintenance;
import com.etiya.RentACar.entites.Rental;
import com.etiya.RentACar.entites.ComplexTypes.RentalDetail;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private UserService userService;

	@Autowired
	private RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService, CarService carService,
			UserService userService) {
		super();
		this.rentalDao = rentalDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.userService = userService;
	}

	@Override
	public DataResult<List<RentalSearchListDto>> getAll() {
		List<Rental> result = this.rentalDao.findAll();

		List<RentalSearchListDto> response = result.stream()
				.map(rental -> modelMapperService.forDto().map(rental, RentalSearchListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentalSearchListDto>>(response, "Rental liste");
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		Result result = BusinessRules.run(checkCarExists(createRentalRequest.getCarId()),
				checkCarIsReturned(createRentalRequest.getCarId()),
				checkUserExists(createRentalRequest.getUserId()),
				checkCompareUserAndCarFindeksScore(createRentalRequest.getUserId(),
						createRentalRequest.getCarId()),
				checkCarIsMaintenance(createRentalRequest.getCarId()));
		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rentalDao.save(rental);
		return new SuccessResult("Araba kiralandı");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Result result = BusinessRules.run(checkRentalExists(deleteRentalRequest.getRentalId()));
		if (result != null) {
			return result;
		}
		Rental rental = modelMapperService.forRequest().map(deleteRentalRequest, Rental.class);
		rentalDao.delete(rental);
		return new SuccessResult("Araba silindi");
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		Result resultCheck = BusinessRules.run(checkCarExists(updateRentalRequest.getCarId()),
				checkRentalExists(updateRentalRequest.getRentalId()),
				checkUserExists(updateRentalRequest.getUserId()),
				checkCarIsMaintenance(updateRentalRequest.getCarId()));
		
		if (resultCheck != null) {
			return resultCheck;
		}
		
		Rental rental = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		RentalSearchListDto result = this.rentalDao.getRentalDetails(updateRentalRequest.getRentalId());
		rental.setRentDate(result.getRentDate());
		this.rentalDao.save(rental);
		return new SuccessResult("Updated");

	}

	public Result checkCarIsReturned(int carId) {// burayı kontrol et updete etmıoyor 
		RentalDetail rental = this.rentalDao.getByCarIdWhereReturnDateIsNull(carId);
		if (rental != null) {
			return new ErrorResult("Araç şuan da müsait değil.");
		}
		return new SuccessResult();
	}

	private Result checkCarIsMaintenance(int carId) {
		MaintenanceDto maintenanceDto = this.rentalDao.getByCarIdWhereMaintenanceReturnDateIsNull(carId);
		if (maintenanceDto != null) {
			return new ErrorResult("Araç şuan bakımda ve müsait değil");
		}
		return new SuccessResult();
	}

	private Result checkCompareUserAndCarFindeksScore(int userId, int carId) {
		DataResult<CarSearchListDto> car = this.carService.getById(carId);
		int user = this.userService.getById(userId).getData().getFindeksScore();
		if (car.getData().getMinFindeksScore() >= user) {
			return new ErrorResult("findeksScore not insufficient ");
		}
		return new SuccessResult();
	}

	private Result checkUserExists(int userId) {
		if(!this.userService.existsById(userId).isSuccess()) {
			return new ErrorResult("user bulunamadı");
		}
		return new SuccessResult();
	}
	
	private Result checkRentalExists(int rentalId) {
		if(!this.rentalDao.existsById(rentalId)) {
			return new ErrorResult("rental bulunamadı");
		}
		return new SuccessResult();
	}
	
	private Result checkCarExists(int carId) {
		if (!this.carService.checkCarExists(carId).isSuccess()) {
			return new ErrorResult("araç bulunamadı");
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<RentalSearchListDto> getByRentalId(int rentalId) {
		Rental rental =   this.rentalDao.getById(rentalId);
		RentalSearchListDto result = modelMapperService.forDto().map(rental, RentalSearchListDto.class);		
		return new SuccessDataResult<RentalSearchListDto>(result);
	}

}
