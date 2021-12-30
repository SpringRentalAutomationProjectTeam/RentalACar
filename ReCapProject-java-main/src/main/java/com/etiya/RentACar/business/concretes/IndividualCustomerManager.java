package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACar.business.abstracts.CreditCardService;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.UserService;
import com.etiya.RentACar.business.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.IndividualCustomerService;

import com.etiya.RentACar.business.dtos.IndividualCustomerSearchListDto;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.CreateIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.DeleteIndividualCustomerRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.UpdateIndividualCustomerRequest;

import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.RentACar.entites.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

    private IndividualCustomerDao individualCustomerDao;
    private ModelMapperService modelMapperService;
    private LanguageWordService languageWordService;
    private CreditCardService creditCardService;
    private UserService userService;

    @Autowired
    private IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,LanguageWordService languageWordService,
                                      ModelMapperService modelMapperService,CreditCardService creditCardService,UserService userService) {

        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
        this.languageWordService = languageWordService;
        this.creditCardService=creditCardService;
        this.userService = userService;
    }

    @Override
    public DataResult<List<IndividualCustomerSearchListDto>> getAll() {
        List<IndividualCustomer> result = this.individualCustomerDao.findAll();
        List<IndividualCustomerSearchListDto> response = result.stream()
                .map(customerIndividual -> modelMapperService.forDto()
                        .map(customerIndividual, IndividualCustomerSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<IndividualCustomerSearchListDto>>(response, this.languageWordService.getValueByKey(Messages.CUSTOMERLIST).getData());
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
        IndividualCustomer result = modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.save(result);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CUSTOMERADD).getData());
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
        Result result = BusinessRules.run(checkIfUserExists(updateIndividualCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        IndividualCustomer individualCustomerResult = modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
        individualCustomerResult.setFindeksScore(this.userService.getById(updateIndividualCustomerRequest.getUserId()).getData().getFindeksScore());

        this.individualCustomerDao.save(individualCustomerResult);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CUSTOMERUPDATE).getData());
    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
        Result result = BusinessRules.run(checkIfUserExists(deleteIndividualCustomerRequest.getUserId())
                ,checkIfIsThereCreditCardOfThisUser(deleteIndividualCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        IndividualCustomer individualCustomerResult = modelMapperService.forRequest().map(deleteIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.delete(individualCustomerResult);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.CUSTOMERDELETE).getData());
    }

    private Result checkIfUserExists(int id) {
        if (!this.individualCustomerDao.existsById(id)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.USERNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfIsThereCreditCardOfThisUser(int userId){
        if (this.creditCardService.getByUserId(userId).isSuccess()){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.CREDITCARDDELETEERROR).getData());
        }
        return new SuccessResult();
    }

}
