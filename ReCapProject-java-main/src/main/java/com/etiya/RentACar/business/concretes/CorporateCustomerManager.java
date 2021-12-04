package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import antlr.debug.MessageAdapter;
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

    @Autowired
    public CorporateCustomerManager(CorporateCustomersDao corporateCustomersDao,
                                    ModelMapperService modelMapperService) {
        super();
        this.corporateCustomersDao = corporateCustomersDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CorporateCustomerSearchListDto>> getAll() {
        List<CorporateCustomer> result = this.corporateCustomersDao.findAll();
        List<CorporateCustomerSearchListDto> response = result.stream()
                .map(corporateCustomer -> modelMapperService.forDto()
                        .map(corporateCustomer, CorporateCustomerSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CorporateCustomerSearchListDto>>(response, Messages.CUSTOMERLIST);
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
        CorporateCustomer result = modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomersDao.save(result);
        return new SuccessResult(Messages.CUSTOMERADD);
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
        Result result = BusinessRules.run(checkIfUserExists(updateCorporateCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        CorporateCustomer corporateCustomer = modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomersDao.save(corporateCustomer);
        return new SuccessResult(Messages.CUSTOMERUPDATE);
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
        Result result = BusinessRules.run(checkIfUserExists(deleteCorporateCustomerRequest.getUserId()));
        if (result != null) {
            return result;
        }

        CorporateCustomer corporateCustomer = modelMapperService.forRequest().map(deleteCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomersDao.delete(corporateCustomer);
        return new SuccessResult(Messages.CUSTOMERDELETE);
    }

    private Result checkIfUserExists(int id) {
        var result = this.corporateCustomersDao.existsById(id);
        if (!result) {
            return new ErrorResult(Messages.CUSTOMERNOTFOUND);
        }
        return new SuccessResult();
    }

}
