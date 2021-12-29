package com.etiya.RentACar.business.abstracts;

import java.util.List;

import com.etiya.RentACar.business.dtos.CreditCardDto;
import com.etiya.RentACar.business.requests.creditCard.CreateCreditCardRequest;
import com.etiya.RentACar.business.requests.creditCard.DeleteCreditCardRequest;
import com.etiya.RentACar.business.requests.creditCard.UpdateCreditCardRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

public interface CreditCardService {

	DataResult<List<CreditCardDto>> getAll();

	Result add(CreateCreditCardRequest createCreditCardRequest);

	Result update(UpdateCreditCardRequest updateCreditCardRequest);

	Result delete(DeleteCreditCardRequest deleteCarRequest);

	Result getByUserId(int userId);

}
