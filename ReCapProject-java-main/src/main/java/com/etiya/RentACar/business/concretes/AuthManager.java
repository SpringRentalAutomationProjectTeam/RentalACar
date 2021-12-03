package com.etiya.RentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.AuthService;
import com.etiya.RentACar.business.abstracts.CorporateCustomerService;
import com.etiya.RentACar.business.abstracts.IndividualCustomerService;
import com.etiya.RentACar.business.abstracts.UserService;
import com.etiya.RentACar.business.requests.LoginRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.CreateIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.RegisterIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.corporateCustomers.CreateCorporateCustomerRequest;
import com.etiya.RentACar.business.requests.corporateCustomers.RegisterCorporateCustomerRequest;
import com.etiya.RentACar.core.utilities.adapters.findex.CustomerFindexScoreService;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessResult;

@Service
public class AuthManager implements AuthService {
	
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	private UserService userService;
	private ModelMapperService modelMapperService;
	private CustomerFindexScoreService customerFindexScoreService;
	
	@Autowired
	public AuthManager(IndividualCustomerService individualCustomerService, UserService userService,
			ModelMapperService modelMapperService, CustomerFindexScoreService customerFindexScoreService
			,CorporateCustomerService corporateCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
		this.userService = userService;
		this.modelMapperService = modelMapperService;
		this.customerFindexScoreService = customerFindexScoreService;
		this.corporateCustomerService = corporateCustomerService;
	}
	
	
	@Override
	public Result individualCustomerRegister(RegisterIndividualCustomerRequest registerIndividualCustomerRequest) {
		
		
		CreateIndividualCustomerRequest result = modelMapperService.forRequest()
				.map(registerIndividualCustomerRequest, CreateIndividualCustomerRequest.class);
		
		result.setFindeksScore(customerFindexScoreService.getIndividualFindeksScore());
		this.individualCustomerService.add(result);
		return new SuccessResult("Customer added");
		
	}

	@Override
	public Result login(LoginRequest loginRequest) {
		Result result = BusinessRules.run(this.checkCustomerEmailByEmailIsMatched(loginRequest),
				this.checkCustomerPasswordByPasswordIsMatched(loginRequest));

		if (result != null) {
			return result;
		}

		return new SuccessResult("Successfuly login");
	}
	
	private Result checkCustomerEmailByEmailIsMatched(LoginRequest loginRequest) {

		if (this.userService.existsByEmail(loginRequest.getEmail()).isSuccess()) {
			return new ErrorResult("Email hatalı");
		}
		return new SuccessResult();
	}
	
	private Result checkCustomerPasswordByPasswordIsMatched(LoginRequest loginRequest) {

		if (checkCustomerEmailByEmailIsMatched(loginRequest).isSuccess()) {

			if (!this.userService.getByEmail(loginRequest.getEmail()).getData().getPassword()
					.equals(loginRequest.getPassword())) {
				return new ErrorResult("Password hatalı");
			}
		}
		return new SuccessResult();
	}
	
	private Result checkUserExists(int userId) {
		if(!this.userService.existsById(userId).isSuccess()) {
			return new ErrorResult("kullanıcı bulunamadı");
		}
		return new SuccessResult();
	}


	@Override
	public Result corporateCustomerRegister(RegisterCorporateCustomerRequest registerCorporateCustomerRequest) {
		CreateCorporateCustomerRequest result = modelMapperService.forRequest()
				.map(registerCorporateCustomerRequest, CreateCorporateCustomerRequest.class);
		
		result.setFindeksScore(customerFindexScoreService.getCorporateFindeksScore());
		this.corporateCustomerService.add(result);
		return new SuccessResult("Customer added");
	}

}
