package com.etiya.RentACar.business.concretes;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.abstracts.InvoiceService;
import com.etiya.RentACar.business.abstracts.RentalService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.dtos.InvoiceSearchListDto;
import com.etiya.RentACar.business.dtos.RentalSearchListDto;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.etiya.RentACar.business.requests.Invoice.DeleteInvoiceRequest;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.InvoiceDao;
import com.etiya.RentACar.entites.Invoice;
import com.etiya.RentACar.entites.Rental;

public class InvoiceManager implements InvoiceService {
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private CarService carService;

	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, RentalService rentalService,
			CarService carService) {
		super();
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.carService = carService;
	}

	@Override
	public DataResult<List<InvoiceSearchListDto>> getAll() {
		List<Invoice> result = this.invoiceDao.findAll();
		List<InvoiceSearchListDto> response = result.stream()
				.map(invoice -> modelMapperService.forDto().map(invoice, InvoiceSearchListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<InvoiceSearchListDto>>(response, Messages.ListedCar);
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
//		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
//
//		RentalSearchListDto rental = rentalService.getByRentalId(createInvoiceRequest.getRentalId()).getData();
//		CarSearchListDto car= this.carService.getById(rental.getCarId()).getData();
//		
//		
//		LocalDate returnDateCount = rental.getReturnDate();
//		LocalDate rentDateCount = rental.getRentDate();
//		Period period = Period.between(returnDateCount, rentDateCount);
//		
//		double totalAmount = car.getDailyPrice() * period.getDays();
//		invoice.setTotalRentalDay(period.getDays());
//		invoice.setInvoiceDate(LocalDate.now());
//		invoice.setTotalAmount(totalAmount);
//		invoice.setUser(createInvoiceRequest.get);
//		this.invoiceDao.save(invoice);
		return new SuccessResult();

	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteRentalRequest) {
		return null;
	}

}
