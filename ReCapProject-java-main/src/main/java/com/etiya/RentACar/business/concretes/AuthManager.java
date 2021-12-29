package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.*;
import com.etiya.RentACar.business.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private LanguageWordService languageWordService;

    @Autowired
    public AuthManager(IndividualCustomerService individualCustomerService, UserService userService,
                       ModelMapperService modelMapperService, CustomerFindexScoreService customerFindexScoreService
            , CorporateCustomerService corporateCustomerService,
                       LanguageWordService languageWordService) {
        super();
        this.individualCustomerService = individualCustomerService;
        this.userService = userService;
        this.modelMapperService = modelMapperService;
        this.customerFindexScoreService = customerFindexScoreService;
        this.corporateCustomerService = corporateCustomerService;
        this.languageWordService = languageWordService;
    }

    @Override
    public Result individualCustomerRegister(RegisterIndividualCustomerRequest registerIndividualCustomerRequest) {
        Result resultCheck = BusinessRules.run(isValidEmailAddress(registerIndividualCustomerRequest.getEmail()));
        if (resultCheck != null) {
            return resultCheck;
        }
        CreateIndividualCustomerRequest result = modelMapperService.forRequest()
                .map(registerIndividualCustomerRequest, CreateIndividualCustomerRequest.class);

        result.setFindeksScore(customerFindexScoreService.getIndividualFindeksScore());
        this.individualCustomerService.add(result);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_add").getData());

    }

    @Override
    public Result corporateCustomerRegister(RegisterCorporateCustomerRequest registerCorporateCustomerRequest) {

        Result resultCheck = BusinessRules.run(isValidEmailAddress(registerCorporateCustomerRequest.getEmail()));
        if (resultCheck != null) {
            return resultCheck;
        }

        CreateCorporateCustomerRequest result = modelMapperService.forRequest()
                .map(registerCorporateCustomerRequest, CreateCorporateCustomerRequest.class);

        result.setFindeksScore(customerFindexScoreService.getCorporateFindeksScore());
        this.corporateCustomerService.add(result);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_add").getData());
    }

    @Override
    public Result login(LoginRequest loginRequest) {
        Result result = BusinessRules.run(this.checkCustomerEmailByEmailIsMatched(loginRequest),
                this.checkCustomerPasswordByPasswordIsMatched(loginRequest),isValidEmailAddress(loginRequest.getEmail()));

        if (result != null) {
            return result;
        }

        return new SuccessResult(this.languageWordService.getValueByKey("login_success").getData());
    }

    private Result checkCustomerEmailByEmailIsMatched(LoginRequest loginRequest) {

        if (this.userService.checkIfEmailExists(loginRequest.getEmail()).isSuccess()) {
            return new ErrorResult(this.languageWordService.getValueByKey("email_error").getData());
        }
        return new SuccessResult();
    }

    private Result checkCustomerPasswordByPasswordIsMatched(LoginRequest loginRequest) {

        if (checkCustomerEmailByEmailIsMatched(loginRequest).isSuccess()) {

            if (!this.userService.getByEmail(loginRequest.getEmail()).getData().getPassword()
                    .equals(loginRequest.getPassword())) {
                return new ErrorResult(this.languageWordService.getValueByKey("login_password_error").getData());
            }
        }
        return new SuccessResult();
    }

    private Result isValidEmailAddress(String email) {
        String ePattern = "^[\\w!#$%&’+/=?`{|}~^-]+(?:\\.[\\w!#$%&’+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);

        if (!m.matches()){
            return new ErrorResult(this.languageWordService.getValueByKey("email_format_error").getData());
        }
        return new SuccessResult();
    }
}
