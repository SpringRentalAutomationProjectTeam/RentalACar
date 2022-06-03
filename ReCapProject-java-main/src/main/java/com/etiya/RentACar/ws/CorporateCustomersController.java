package com.etiya.RentACar.ws;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACar.business.abstracts.CorporateCustomerService;
import com.etiya.RentACar.business.dtos.CorporateCustomerSearchListDto;
import com.etiya.RentACar.business.requests.corporateCustomers.DeleteCorporateCustomerRequest;
import com.etiya.RentACar.business.requests.corporateCustomers.UpdateCorporateCustomerRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

@RestController
@RequestMapping("api/corporateCustomer/")
public class CorporateCustomersController {

	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		super();
		this.corporateCustomerService = corporateCustomerService;
	}
	@GetMapping("getAll")
	public DataResult<List<CorporateCustomerSearchListDto>> getAll() {
		return this.corporateCustomerService.getAll();
	}
	
	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}
	
	@DeleteMapping("delete")
	public Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}
	
}
