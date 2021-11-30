package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.abstracts.MaintenanceService;
import com.etiya.RentACar.business.abstracts.RentalService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.dtos.MaintenanceDto;
import com.etiya.RentACar.business.dtos.MaintenanceSearchListDto;
import com.etiya.RentACar.business.dtos.RentalSearchListDto;
import com.etiya.RentACar.business.requests.maintenance.CreateMaintenanceRequest;
import com.etiya.RentACar.business.requests.maintenance.DeleteMaintenanceRequest;
import com.etiya.RentACar.business.requests.maintenance.UpdateMaintenanceRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.MaintenanceDao;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {

	private MaintenanceDao maintenanceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private CarService carService;

	@Autowired
	public MaintenanceManager(MaintenanceDao maintenanceDao, ModelMapperService modelMapperService,
			CarService carService, RentalService rentalService) {
		this.maintenanceDao = maintenanceDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentalService = rentalService;
	}

	@Override
	public DataResult<List<MaintenanceSearchListDto>> getAll() {
		List<Maintenance> result = this.maintenanceDao.findAll();
		List<MaintenanceSearchListDto> response = result.stream()
				.map(maintenance -> modelMapperService.forDto().map(maintenance, MaintenanceSearchListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<MaintenanceSearchListDto>>(response, "Arac bakımda olan araçlar");
	}

	@Override
	public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
		Result result = BusinessRules.run(checkCarExists(createMaintenanceRequest.getCarId()),
				checkByCarReturnFromRental(createMaintenanceRequest.getCarId()));

		if (result != null) {
			return result;
		}

		Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
		this.maintenanceDao.save(maintenance);
		return new SuccessResult("Araç Bakıma Gönderildi");
	}

	@Override
	public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {//yeni ekledi 
		Result result = BusinessRules.run(checkMaintenanceExists(updateMaintenanceRequest.getMaintenanceId()),
				checkCarExists(updateMaintenanceRequest.getCarId()),
				checkByCarReturnFromRental(updateMaintenanceRequest.getCarId()));

		if (result != null) {
			return result;
		}

		Maintenance maintenance = this.modelMapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);

		// MaintenanceDto result = getById(updateMaintenanceRequest.getId()).getData();
		this.maintenanceDao.save(maintenance);
		return new SuccessResult("Araç Bakımı güncellendi");
	}

	@Override
	public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
		Result result = BusinessRules.run(checkMaintenanceExists(deleteMaintenanceRequest.getMaintenanceId()));

		if (result != null) {
			return result;
		}
		this.maintenanceDao.deleteById(deleteMaintenanceRequest.getMaintenanceId());
		return new SuccessResult("Araç Bakım Listesinden Silindi");
	}

	@Override
	public DataResult<MaintenanceSearchListDto> getById(int maintenanceId) {

		Maintenance maintenance = this.maintenanceDao.findById(maintenanceId).get();
		MaintenanceSearchListDto response = this.modelMapperService.forDto().map(maintenance,
				MaintenanceSearchListDto.class);
		return new SuccessDataResult<MaintenanceSearchListDto>(response);
	}

	// Kiralanmış araç bakıma gidemez
	private Result checkByCarReturnFromRental(int carId) {
		if (!this.rentalService.checkCarIsReturned(carId).isSuccess()) {
			return new ErrorResult("Araç şuan da bakıma gidemez.");
		}
		return new SuccessResult();

	}

	private Result checkCarExists(int carId) {
		if (!this.carService.checkCarExists(carId).isSuccess()) {
			return new ErrorResult("araba bulunamadı");
		}
		return new SuccessResult();
	}

	private Result checkMaintenanceExists(int maintenanceId) {
		if (!this.maintenanceDao.existsById(maintenanceId)) {
			return new ErrorResult("maintenance bulunamadı");
		}
		return new SuccessResult();
	}
	
	@Override
	public Result checkCarIsMaintenance(int carId) {
		MaintenanceDto maintenanceDto = this.maintenanceDao.getByCarIdWhereReturnDateIsNull(carId);
		if (maintenanceDto != null) {
			return new ErrorResult("Araç şuan da bakımda ve müsait değil.");
		}
		return new SuccessResult();
	}

}
