package com.etiya.RentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import antlr.debug.MessageAdapter;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.core.utilities.results.*;
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
import com.etiya.RentACar.dataAccess.abstracts.MaintenanceDao;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.Maintenance;

@Service
public class MaintenanceManager implements MaintenanceService {

    private MaintenanceDao maintenanceDao;
    private ModelMapperService modelMapperService;
    private RentalService rentalService;
    private CarService carService;
    private LanguageWordService languageWordService;

    @Autowired
    public MaintenanceManager(MaintenanceDao maintenanceDao, ModelMapperService modelMapperService,
                              CarService carService, RentalService rentalService,
                              LanguageWordService languageWordService) {
        this.maintenanceDao = maintenanceDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.rentalService = rentalService;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<MaintenanceSearchListDto>> getAll() {
        List<Maintenance> result = this.maintenanceDao.findAll();
        List<MaintenanceSearchListDto> response = result.stream()
                .map(maintenance -> modelMapperService.forDto().map(maintenance, MaintenanceSearchListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<MaintenanceSearchListDto>>(response, this.languageWordService.getValueByKey(Messages.CARMAINTENANCELIST).getData());
    }

    @Override
    public Result add(CreateMaintenanceRequest createMaintenanceRequest) {
        Result result = BusinessRules.run(checkIfCarExists(createMaintenanceRequest.getCarId()),
                checkByCarReturnFromRental(createMaintenanceRequest.getCarId()));
        if (result != null) {
            return result;
        }

        Maintenance maintenance = this.modelMapperService.forRequest().map(createMaintenanceRequest, Maintenance.class);
        this.maintenanceDao.save(maintenance);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CARMAINTENANCEADD).getData());
    }

    @Override
    public Result update(UpdateMaintenanceRequest updateMaintenanceRequest) {
        Result result = BusinessRules.run(checkIfMaintenanceExists(updateMaintenanceRequest.getMaintenanceId()),
                checkIfCarExists(updateMaintenanceRequest.getCarId()),
                checkByCarReturnFromRental(updateMaintenanceRequest.getCarId()));
        if (result != null) {
            return result;
        }

        Maintenance maintenance = this.modelMapperService.forRequest().map(updateMaintenanceRequest, Maintenance.class);
        this.maintenanceDao.save(maintenance);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CARMAINTENANCEUPDATE).getData());
    }

    @Override
    public Result delete(DeleteMaintenanceRequest deleteMaintenanceRequest) {
        Result result = BusinessRules.run(checkIfMaintenanceExists(deleteMaintenanceRequest.getMaintenanceId()));
        if (result != null) {
            return result;
        }

        this.maintenanceDao.deleteById(deleteMaintenanceRequest.getMaintenanceId());
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CARMAINTENANCEDELETE).getData());
    }

    @Override
    public DataResult<MaintenanceSearchListDto> getById(int maintenanceId) {

        Result resultCheck = BusinessRules.run(checkIfMaintenanceExists(maintenanceId));
        if (resultCheck != null) {
            return new ErrorDataResult(resultCheck);
        }

        Maintenance maintenance = this.maintenanceDao.findById(maintenanceId).get();
        MaintenanceSearchListDto response = this.modelMapperService.forDto().map(maintenance,
                MaintenanceSearchListDto.class);
        return new SuccessDataResult<MaintenanceSearchListDto>(response,this.languageWordService.getValueByKey(Messages.CARMAINTENANCELIST).getData());
    }

    @Override
    public Result checkIfCarIsMaintenance(int carId) {
        MaintenanceDto maintenanceDto = this.maintenanceDao.getByCarIdWhereReturnDateIsNull(carId);
        if (maintenanceDto != null) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARMAINTENANCEERROR).getData());
        }
        return new SuccessResult();
    }

    private Result checkByCarReturnFromRental(int carId) {
        if (!this.rentalService.checkIfCarIsReturned(carId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARMAINTENANCERENTALERROR).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCarExists(int carId) {
        if (!this.carService.checkIfCarExists(carId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfMaintenanceExists(int maintenanceId) {
        if (!this.maintenanceDao.existsById(maintenanceId)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARMAINTENANCENOTFOUND).getData());
        }
        return new SuccessResult();
    }

}
