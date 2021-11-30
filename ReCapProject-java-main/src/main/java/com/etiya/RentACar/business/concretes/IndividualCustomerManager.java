package com.etiya.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<IndividualCustomerSearchListDto>> getAll() {
		List<IndividualCustomer> result=this.individualCustomerDao.findAll();
		List<IndividualCustomerSearchListDto> response=result.stream()
				.map(customerIndividual-> modelMapperService.forDto()
						.map(customerIndividual,IndividualCustomerSearchListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<IndividualCustomerSearchListDto>>(response);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		IndividualCustomer result =modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(result);
		return new SuccessResult("Customer added");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		Result result=BusinessRules.run(checkUserExists(updateIndividualCustomerRequest.getUserId()));
		if (result!=null) {
			return result;
		}
		IndividualCustomer individualCustomerResult =modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomerResult);
		return new SuccessResult("Customer added");
	}
	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		Result result=BusinessRules.run(checkUserExists(deleteIndividualCustomerRequest.getUserId()));
		if (result!=null) {
			return result;
		}
		IndividualCustomer individualCustomerResult =modelMapperService.forRequest().map(deleteIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.delete(individualCustomerResult);
		return new SuccessResult("CustomerDeleted");
	}
	private Result checkUserExists(int id) {
        var result = this.individualCustomerDao.existsById(id);
        if (!result) {
            return new ErrorResult("Kullanıcı bulunamadı.");

        }
        return new SuccessResult("Kullanıcı bulundu.");
    }

}
