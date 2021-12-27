package com.etiya.RentACar.business.abstracts;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.etiya.RentACar.business.dtos.InvoiceSearchListDto;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceDateRequest;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.etiya.RentACar.business.requests.Invoice.DeleteInvoiceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Rental;

public interface InvoiceService {

	DataResult<List<InvoiceSearchListDto>> getAll();
	Result add(CreateInvoiceRequest createInvoiceRequest);
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
	DataResult<List<InvoiceSearchListDto>> getRentingInvoiceByUserId(int userId);
	DataResult<List<InvoiceSearchListDto>> getByCreateDateBetweenBeginDateAndEndDate(LocalDate beginDate, LocalDate endDate);
	//Result updateInvoiceIfReturnDateIsNotNull(Rental rental, int extra, int additionalTotalPrice);


	
}
