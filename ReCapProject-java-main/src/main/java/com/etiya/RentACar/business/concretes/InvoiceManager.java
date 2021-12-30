package com.etiya.RentACar.business.concretes;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACar.business.abstracts.*;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.*;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceDateRequest;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.etiya.RentACar.business.requests.Invoice.DeleteInvoiceRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;
import com.etiya.RentACar.dataAccess.abstracts.InvoiceDao;
import com.etiya.RentACar.entites.Invoice;
import com.etiya.RentACar.entites.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.NimbusStyle;

@Service
public class InvoiceManager implements InvoiceService {
    private InvoiceDao invoiceDao;
    private ModelMapperService modelMapperService;
    private RentalService rentalService;
    private CarService carService;
    private UserService userService;
    private RentalAdditionalService rentalAdditionalService;
    private AdditionalRentalItemService additionalRentalItemService;
    private LanguageWordService languageWordService;

    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, @Lazy RentalService rentalService,
                          CarService carService, UserService userService,
                          RentalAdditionalService rentalAdditionalService,
     AdditionalRentalItemService additionalRentalItemService,LanguageWordService languageWordService) {
        this.invoiceDao = invoiceDao;
        this.modelMapperService = modelMapperService;
        this.rentalService = rentalService;
        this.userService = userService;
        this.carService = carService;
        this.rentalAdditionalService=rentalAdditionalService;
        this.additionalRentalItemService = additionalRentalItemService;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<InvoiceSearchListDto>> getAll() {
        List<Invoice> result = this.invoiceDao.findAll();
        List<InvoiceSearchListDto> response = result.stream().map(invoice -> modelMapperService.forDto().map(invoice, InvoiceSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<InvoiceSearchListDto>>(response, this.languageWordService.getValueByKey(Messages.INVOICELIST).getData());
    }
    @Override
    public DataResult<List<InvoiceSearchListDto>> getRentingInvoiceByUserId(int userId) {
        Result resultCheck = BusinessRules.run(checkIfUserExists(userId),
                checkIsThereInvoiceOfUser(userId));
        if (resultCheck != null) {
            return new ErrorDataResult(resultCheck);
        }
        List<Invoice> result = invoiceDao.getByRental_User_Id(userId);
        List<InvoiceSearchListDto> response = result.stream()
                .map(invoice -> modelMapperService.forDto()
                        .map(invoice, InvoiceSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<InvoiceSearchListDto>>(response,this.languageWordService.getValueByKey(Messages.INVOICENOTFOUND).getData());
    }

    @Override
    public Result add(CreateInvoiceRequest createInvoiceRequest) {
        Result result = BusinessRules.run(
                checkIfRentalExists(createInvoiceRequest.getRentalId()));
        if (result != null) {
            return result;
        }

        RentalSearchListDto rental = rentalService.getByRentalId(createInvoiceRequest.getRentalId()).getData();

        CarSearchListDto car = this.carService.getById(rental.getCarId()).getData();

        LocalDate returnDateCount = rental.getReturnDate();
        LocalDate rentDateCount = rental.getRentDate();
        Period period = Period.between(rentDateCount, returnDateCount);
        int days = period.getDays();
        double totalAmount = days *
                (car.getDailyPrice() + getAdditionalItemsTotalPriceByRentalId(createInvoiceRequest.getRentalId()))
                +priceOnDifferentCity(createInvoiceRequest.getRentalId());
        Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
        invoice.setTotalRentalDay(days);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setTotalAmount(totalAmount);
        invoice.setInvoiceNumber(createInvoiceNumber(rental.getRentalId()).getData());
        this.invoiceDao.save(invoice);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.INVOICEADD).getData());
    }

    @Override
    public Result delete(DeleteInvoiceRequest deleteRentalRequest) {

        Result result = BusinessRules.run(checkIfInvoiceExists(deleteRentalRequest.getInvoiceId()));
        if (result!=null){
            return result;
        }

        Invoice invoice = modelMapperService.forDto().map(deleteRentalRequest, Invoice.class);
        this.invoiceDao.delete(invoice);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.INVOICEDELETE).getData());
    }



    @Override
    public DataResult<List<InvoiceSearchListDto>> getByCreateDateBetweenBeginDateAndEndDate(LocalDate beginDate, LocalDate endDate) {
        List<Invoice> invoices = this.invoiceDao.findByInvoiceDateBetween(beginDate, endDate);
        List<InvoiceSearchListDto> invoiceSearchListDtos = invoices.stream()
                .map(invoice -> modelMapperService.forDto().map(invoice, InvoiceSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<InvoiceSearchListDto>>(invoiceSearchListDtos,this.languageWordService.getValueByKey(Messages.INVOICEBYCUSTOMERLIST).getData());
    }


    private DataResult<String> createInvoiceNumber(int rentalId) {
        LocalDate now = LocalDate.now();
        String currentYear = String.valueOf(now.getYear());
        String currentMonth = String.valueOf(now.getMonthValue());
        String rentalIdS = String.valueOf(rentalId);
        String invoiceNumber = currentYear + currentMonth + "-" + rentalIdS;
        return new SuccessDataResult<>(invoiceNumber);
    }

    private DataResult<Integer> totalRentDays(Rental rental) {
        Period period = Period.between(rental.getRentDate(), rental.getReturnDate());
        return new SuccessDataResult<Integer>(period.getDays());
    }

    private Result checkIfRentalExists(int rentalId) {

        if (this.invoiceDao.existsByRental_RentalId(rentalId)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.INVOICENOTADD).getData());
        }
        return new SuccessResult();
    }

    private Result checkIsThereInvoiceOfUser(int userId){
        if (!this.invoiceDao.existsByRental_UserId(userId)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.INVOICEUSERERROR).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfInvoiceExists(int invoiceId){
        if (!this.invoiceDao.existsById(invoiceId)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.INVOICENOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfUserExists(int userId) {
        if (!userService.checkIfUserExists(userId).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.USERNOTFOUND).getData());
        }
        return new SuccessResult(Messages.USERFOUND);
    }

    public int getAdditionalItemsTotalPriceByRentalId(int rentalId) {//1-1 1-3 1-2
        List<AdditionalRentalItemSearchListDto> result = this.additionalRentalItemService.getByRentalId(rentalId).getData();
        int totalAmount=0;
        for(AdditionalRentalItemSearchListDto item : result){
           int id= item.getAdditionalServiceId();
            totalAmount+=this.rentalAdditionalService.getById(id).getData().getServiceDailyPrice();
        }
        return totalAmount;
    }

    private int priceOnDifferentCity(int rentalId) {

         RentalSearchListDto rt=  this.rentalService.getByRentalId(rentalId).getData();
        if (rt.getRentCity()!= rt.getReturnCity()) {
            return 500;
        } else {
            return 0;
        }
    }


}
