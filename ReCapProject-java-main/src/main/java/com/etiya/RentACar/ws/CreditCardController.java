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

import com.etiya.RentACar.business.abstracts.CreditCardService;
import com.etiya.RentACar.business.dtos.CreditCardDto;
import com.etiya.RentACar.business.requests.creditCard.CreateCreditCardRequest;
import com.etiya.RentACar.business.requests.creditCard.DeleteCreditCardRequest;
import com.etiya.RentACar.business.requests.creditCard.UpdateCreditCardRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

@RestController
@RequestMapping("api/creditCard/")
public class CreditCardController {

	private CreditCardService creditCardService;

	@Autowired
	public CreditCardController(CreditCardService creditCardService) {
		super();
		this.creditCardService = creditCardService;
	}
	@GetMapping("all")
	public DataResult<List<CreditCardDto>> getAll(){
		return this.creditCardService.getAll();
	}
	@PostMapping("add") 
	public Result add(@RequestBody @Valid CreateCreditCardRequest createCreditCardRequest) {
		return this.creditCardService.add(createCreditCardRequest);
	}
	
	@PutMapping("update") 
	public Result update(@RequestBody @Valid UpdateCreditCardRequest updateCreditCardRequest) {
		return this.creditCardService.update(updateCreditCardRequest);
	}
	@DeleteMapping("delete") 
	public Result delete(@RequestBody @Valid DeleteCreditCardRequest deleteCreditCardRequest) {
		return this.creditCardService.delete(deleteCreditCardRequest);
	}
}
