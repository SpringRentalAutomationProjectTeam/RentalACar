package com.etiya.RentACar.business.abstracts;

import java.util.List;

import com.etiya.RentACar.business.dtos.InvoiceSearchListDto;
import com.etiya.RentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.etiya.RentACar.business.requests.Invoice.DeleteInvoiceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

public interface InvoiceService {

	DataResult<List<InvoiceSearchListDto>> getAll();
	Result add(CreateInvoiceRequest createInvoiceRequest);
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
	
}
