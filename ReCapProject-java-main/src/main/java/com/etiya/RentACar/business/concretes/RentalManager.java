package com.etiya.RentACar.business.concretes;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACar.business.abstracts.*;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.*;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.etiya.RentACar.business.requests.PosServiceRequest;
import com.etiya.RentACar.core.utilities.adapters.fakePos.PaymentByFakePosService;
import com.etiya.RentACar.core.utilities.adapters.fakePos.PaymentByFakePosServiceAdapter;
import com.etiya.RentACar.core.utilities.results.*;
import com.etiya.RentACar.entites.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.requests.Rental.CreateRentalRequest;
import com.etiya.RentACar.business.requests.Rental.DeleteRentalRequest;
import com.etiya.RentACar.business.requests.Rental.UpdateRentalRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
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
    private PaymentByFakePosService paymentByFakePosService;
    private RentalAdditionalService rentalAdditionalService;
    private LanguageWordService languageWordService;


    @Autowired
    private RentalManager(RentalDao rentalDao, ModelMapperService modelMapperService, CarService carService,
                          UserService userService, @Lazy InvoiceService invoiceService, @Lazy CityService cityService
            ,LanguageWordService languageWordService
            , PaymentByFakePosServiceAdapter paymentByFakePosService, RentalAdditionalService rentalAdditionalService) {
        this.languageWordService=languageWordService;
        this.rentalDao = rentalDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.userService = userService;
        this.invoiceService = invoiceService;
        this.rentalAdditionalService = rentalAdditionalService;
        this.cityService = cityService;
        this.paymentByFakePosService = paymentByFakePosService;
    }

    @Override
    public DataResult<List<RentalSearchListDto>> getAll() {
        List<Rental> result = this.rentalDao.findAll();
        List<RentalSearchListDto> response = result.stream()
                .map(rental -> modelMapperService.forDto().map(rental, RentalSearchListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentalSearchListDto>>(response,this.languageWordService.getValueByKey(Messages.RENTALLIST).getData());
    }

    @Override
    public DataResult<RentalSearchListDto> getByRentalId(int rentalId) {
        Result resultcheck = BusinessRules.run(checkIfRentalExists(rentalId));
        if (resultcheck!=null){
            return new ErrorDataResult(resultcheck);
        }

        Rental rental = this.rentalDao.getById(rentalId);
        RentalSearchListDto result = modelMapperService.forDto().map(rental, RentalSearchListDto.class);
        return new SuccessDataResult<RentalSearchListDto>(result, this.languageWordService.getValueByKey(Messages.RENTALNOTFOUND).getData());
    }

    @Override
    public Result add(CreateRentalRequest createRentalRequest) {
        Result result = BusinessRules.run(checkIfCarExists(createRentalRequest.getCarId()),
                checkIfCarIsReturned(createRentalRequest.getCarId()),
                checkIfUserExists(createRentalRequest.getUserId()),
                checkIfCompareUserAndCarFindexScore(createRentalRequest.getUserId(),
                        createRentalRequest.getCarId()),
                checkIfCarIsMaintenance(createRentalRequest.getCarId())
                , checkIfCityExists(createRentalRequest.getRentCityId()));
        if (result != null) {
            return result;
        }

        Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);

        rentalDao.save(rental);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.RENTALADD).getData());
    }

    @Override
    public Result update(UpdateRentalRequest updateRentalRequest) {
        Result resultCheck = BusinessRules.run(
                checkIfIsLimitEnough(updateRentalRequest.getRentalId(), updateRentalRequest.getReturnDate(),
                        updateRentalRequest.getCreditCard()),
                checkIfRentalExists(updateRentalRequest.getRentalId()),
                checkIsRentDateIsAfterThanReturnDate(updateRentalRequest.getRentalId(),updateRentalRequest.getReturnDate()),
                checkIfCityExists(updateRentalRequest.getReturnCityId()));
        if (resultCheck != null) {
            return resultCheck;
        }

        Rental result = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
        Rental response = this.rentalDao.getById(updateRentalRequest.getRentalId());
        response.setReturnDate(result.getReturnDate());
        response.setReturnCity(result.getReturnCity());
        response.setStartKm(response.getStartKm());
        response.setEndKm(updateRentalRequest.getEndKm());


        updateCarKm(response);
        updateCityNameIfReturnCityIsDifferent(response);
        this.rentalDao.save(response);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.RENTALUPDATE).getData());
    }

    @Override
    public Result delete(DeleteRentalRequest deleteRentalRequest) {
        Result result = BusinessRules.run(checkIfRentalExists(deleteRentalRequest.getRentalId()));
        if (result != null) {
            return result;
        }

        Rental rental = modelMapperService.forRequest().map(deleteRentalRequest, Rental.class);
        rentalDao.delete(rental);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.RENTALDELETE).getData());
    }

    @Override
    public Result checkIfCarIsReturned(int carId) {
        RentalDetail rental = this.rentalDao.getByCarIdWhereReturnDateIsNull(carId);
        if (rental != null) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.RENTALDATEERROR).getData());
        }
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.RENTALDATESUCCESS).getData());
    }

    private int totalRentDays(LocalDate rentDate, LocalDate returnDate) {
        Period period = Period.between(rentDate, returnDate);
        return period.getDays();
    }

    private void updateCarKm(Rental rental) {
        this.carService.updateCarKm(rental.getCar().getCarId(), rental.getEndKm());
    }

    private void updateCityNameIfReturnCityIsDifferent(Rental rental) {
        if ((rental.getRentCity().getCityId()) != (rental.getReturnCity().getCityId())) {
            this.carService.updateCarCity(rental.getCar().getCarId(), rental.getReturnCity().getCityId());
        }
    }


    private Result checkIfIsLimitEnough(int rentalId, LocalDate returnDate, CreditCardRentalDto creditCard) {

        Rental rental = this.rentalDao.getById(rentalId);

        CarSearchListDto car = this.carService.getById(rental.getCar().getCarId()).getData();
        double totalAmount = (car.getDailyPrice() * totalRentDays(rental.getRentDate(), returnDate));

        PosServiceRequest posServiceRequest = new PosServiceRequest();
        posServiceRequest.setTotalAmount(totalAmount);
        posServiceRequest.setCvv(creditCard.getCvv());
        posServiceRequest.setExpirationDate(creditCard.getExpirationDate());
        posServiceRequest.setCreditCardNumber(creditCard.getCardNumber());

        if (!this.paymentByFakePosService.withdraw(posServiceRequest)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.INSUFFICIENTBALANCE).getData());
        }
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.SUFFICIENTBALANCE).getData());
    }


    private Result checkIfCarIsMaintenance(int carId) {
        MaintenanceDto maintenanceDto = this.rentalDao.getByCarIdWhereMaintenanceReturnDateIsNull(carId);
        if (maintenanceDto != null) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.RENTALMAINTENANCEERROR).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCompareUserAndCarFindexScore(int userId, int carId) {
        DataResult<CarSearchListDto> car = this.carService.getById(carId);
        int user = this.userService.getById(userId).getData().getFindeksScore();
        if (car.getData().getMinFindeksScore() >= user) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.RENTALFINDEXSCOREERROR).getData());
        }
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.RENTALFINDEXSCORE).getData());
    }

    private Result checkIfUserExists(int userId) {
        if (!this.userService.checkIfUserExists(userId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.USERNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    public Result checkIfRentalExists(int rentalId) {
        if (!this.rentalDao.existsById(rentalId)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.RENTALNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCarExists(int carId) {
        if (!this.carService.checkIfCarExists(carId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CARNOTFOUND).getData());
        }
        return new SuccessResult();
    }


    private Result checkIfCityExists(int cityId) {
        if (!this.cityService.checkIfCityExists(cityId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CITYNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIsRentDateIsAfterThanReturnDate(int rentalID , LocalDate returnDate){
        LocalDate rentDate = this.rentalDao.getById(rentalID).getRentDate();
        Period period = Period.between(rentDate, returnDate);
        if (period.getDays()<0){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.RENTALDATEERROR).getData());
        }
        return new SuccessResult();
    }

}
