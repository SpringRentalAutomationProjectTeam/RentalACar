package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACar.business.abstracts.*;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.etiya.RentACar.entites.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
import com.etiya.RentACar.entites.ComplexTypes.RentalDetail;

@Service
public class RentalManager implements RentalService {

    private RentalDao rentalDao;
    private ModelMapperService modelMapperService;
    private CarService carService;
    private UserService userService;
    private InvoiceService invoiceService;
    private CityService cityService;

    @Autowired
    private RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService, CarService carService,
                          UserService userService, @Lazy InvoiceService invoiceService, @Lazy CityService cityService) {
        super();
        this.rentalDao = rentalDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.cityService = cityService;
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
                checkCarIsMaintenance(createRentalRequest.getCarId())
                , checkByCityId(createRentalRequest.getRentCityId()));
        if (result != null) {
            return result;
        }
        Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
        rentalDao.save(rental);
        return new SuccessResult("Araba kiralandı");
    }

    @Override
    public Result update(UpdateRentalRequest updateRentalRequest) {
        Result resultCheck = BusinessRules.run(
                checkRentalExists(updateRentalRequest.getRentalId()),
                checkByCityId(updateRentalRequest.getReturnCityId()));

        if (resultCheck != null) {
            return resultCheck;
        }

        Rental result = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

        Rental response = this.rentalDao.getById(updateRentalRequest.getRentalId());

        response.setReturnDate(result.getReturnDate());
        response.setReturnCity(result.getReturnCity());
        response.setStartKm(response.getStartKm());
        response.setEndKm(updateRentalRequest.getEndKm());
        updateCityNameIfReturnCityIsDifferent(response);
        updateInvoiceIfReturnDateIsNotNull(response);

        updateCarKm(response);
        this.rentalDao.save(response);
        return new SuccessResult("Updated");

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
    public DataResult<RentalSearchListDto> getByRentalId(int rentalId) {
        Rental rental = this.rentalDao.getById(rentalId);
        RentalSearchListDto result = modelMapperService.forDto().map(rental, RentalSearchListDto.class);
        return new SuccessDataResult<RentalSearchListDto>(result);
    }

    private Result checkByCityId(int cityId) {
        if (!this.cityService.existsByCityId(cityId).isSuccess()) {
            return new ErrorResult("city bulunamadı");
        }
        return new SuccessResult();
    }


    private void updateInvoiceIfReturnDateIsNotNull(Rental rental) {
        if (rental.getReturnDate() != null) {
            this.invoiceService.updateInvoiceIfReturnDateIsNotNull(rental.getRentalId());
        }
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
        if (!this.userService.existsById(userId).isSuccess()) {
            return new ErrorResult("user bulunamadı");
        }
        return new SuccessResult();
    }

    private Result checkRentalExists(int rentalId) {
        if (!this.rentalDao.existsById(rentalId)) {
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

    private void updateCarKm(Rental rental) {
        this.carService.updateCarKm(rental.getCar().getCarId(), rental.getEndKm());
    }

    private void updateCityNameIfReturnCityIsDifferent(Rental rental) {
        if ((rental.getRentCity().getCityId()) != (rental.getReturnCity().getCityId())) {
            this.carService.updateCarCity(rental.getCar().getCarId(), rental.getReturnCity().getCityId());
        }
    }


}
