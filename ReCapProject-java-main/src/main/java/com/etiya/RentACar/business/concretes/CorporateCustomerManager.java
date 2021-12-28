package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import antlr.debug.MessageAdapter;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.constants.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.CorporateCustomerService;
import com.etiya.RentACar.business.dtos.CorporateCustomerSearchListDto;
import com.etiya.RentACar.business.requests.corporateCustomers.CreateCorporateCustomerRequest;
import com.etiya.RentACar.business.requests.corporateCustomers.DeleteCorporateCustomerRequest;
import com.etiya.RentACar.business.requests.corporateCustomers.UpdateCorporateCustomerRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.CorporateCustomersDao;
import com.etiya.RentACar.entites.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

    private CorporateCustomersDao corporateCustomersDao;
    private ModelMapperService modelMapperService;
    private LanguageWordService languageWordService;

    @Autowired
    public CorporateCustomerManager(CorporateCustomersDao corporateCustomersDao,LanguageWordService languageWordService,
                                    ModelMapperService modelMapperService) {
        super();
        this.corporateCustomersDao = corporateCustomersDao;
        this.modelMapperService = modelMapperService;
        this.languageWordService=languageWordService;
    }

    @Override
    public DataResult<List<CorporateCustomerSearchListDto>> getAll() {
        List<CorporateCustomer> result = this.corporateCustomersDao.findAll();
        List<CorporateCustomerSearchListDto> response = result.stream()
                .map(corporateCustomer -> modelMapperService.forDto()
                        .map(corporateCustomer, CorporateCustomerSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CorporateCustomerSearchListDto>>(response, this.languageWordService.getValueByKey("customer_list").getData());
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
        Result resultCheck = BusinessRules.run(checkIfCompanyNameExists(createCorporateCustomerRequest.getCompanyName()));
        if (resultCheck != null) {
            return resultCheck;
        }

        CorporateCustomer result = modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomersDao.save(result);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_add").getData());
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
        Result result = BusinessRules.run(checkIfCompanyNameExists(updateCorporateCustomerRequest.getCompanyName()),
                checkIfUserExists(updateCorporateCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        CorporateCustomer corporateCustomer = modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomersDao.save(corporateCustomer);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_update").getData());
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
        Result result = BusinessRules.run(checkIfUserExists(deleteCorporateCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        CorporateCustomer corporateCustomer = modelMapperService.forRequest().map(deleteCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomersDao.delete(corporateCustomer);
        return new SuccessResult(this.languageWordService.getValueByKey("customer_delete").getData());
    }

    private Result checkIfUserExists(int id) {
        var result = this.corporateCustomersDao.existsById(id);
        if (!result) {
            return new ErrorResult(this.languageWordService.getValueByKey("customer_not_found").getData());
        }
        return new SuccessResult();
    }

    private Result checkIfCompanyNameExists(String companyName) {
        if (this.corporateCustomersDao.existsCorporateCustomerByCompanyName(companyName)) {
            return new ErrorResult(this.languageWordService.getValueByKey("customer_is_already_exists").getData());
        }
        return new SuccessResult();
    }

}
