package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
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

    @Autowired
    private IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,LanguageWordService languageWordService,
                                      ModelMapperService modelMapperService) {

        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<IndividualCustomerSearchListDto>> getAll() {
        List<IndividualCustomer> result = this.individualCustomerDao.findAll();
        List<IndividualCustomerSearchListDto> response = result.stream()
                .map(customerIndividual -> modelMapperService.forDto()
                        .map(customerIndividual, IndividualCustomerSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<IndividualCustomerSearchListDto>>(response, this.languageWordService.getValueByKey("customer_list").getData());
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
        IndividualCustomer result = modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.save(result);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_add").getData());
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
        Result result = BusinessRules.run(checkIfUserExists(updateIndividualCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        IndividualCustomer individualCustomerResult = modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.save(individualCustomerResult);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_update").getData());
    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
        Result result = BusinessRules.run(checkIfUserExists(deleteIndividualCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        IndividualCustomer individualCustomerResult = modelMapperService.forRequest().map(deleteIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.delete(individualCustomerResult);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_delete").getData());
    }

    private Result checkIfUserExists(int id) {
        var result = this.individualCustomerDao.existsById(id);
        if (!result) {
            return new ErrorResult(this.languageWordService.getValueByKey("user_not_found").getData());
        }
        return new SuccessResult();
    }

}
