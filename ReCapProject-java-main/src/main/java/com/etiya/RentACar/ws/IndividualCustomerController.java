package com.etiya.RentACar.ws;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACar.business.abstracts.IndividualCustomerService;
import com.etiya.RentACar.business.dtos.IndividualCustomerSearchListDto;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.CreateIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.DeleteIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.UpdateIndividualCustomerRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

@RestController
@RequestMapping("api/individualCustomer/")
public class IndividualCustomerController {
	
	private IndividualCustomerService individualCustomerService;

	@Autowired
	private IndividualCustomerController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}

	@GetMapping("getAll")
	public DataResult<List<IndividualCustomerSearchListDto>> getAll() {
		return this.individualCustomerService.getAll();
	}
	
	@PutMapping("update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}
	
	@DeleteMapping("delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
}
