package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.requests.LoginRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.CreateIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.RegisterIndividualCustomerRequest;
import com.etiya.RentACar.core.utilities.results.Result;

public interface AuthService {

	Result individualCustomerRegister(RegisterIndividualCustomerRequest registerIndividualCustomerRequest);
	Result login(LoginRequest loginRequest);
}
