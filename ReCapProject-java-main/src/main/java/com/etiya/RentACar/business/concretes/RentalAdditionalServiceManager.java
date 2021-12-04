package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.RentalAdditionalService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.AdditionalServiceSearchListDto;
import com.etiya.RentACar.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;
import com.etiya.RentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.etiya.RentACar.entites.AdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalAdditionalServiceManager implements RentalAdditionalService {
    private AdditionalServiceDao additionalServiceDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public RentalAdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<AdditionalServiceSearchListDto>> getAll() {
        List<AdditionalService> result = this.additionalServiceDao.findAll();
        List<AdditionalServiceSearchListDto> response = result.stream().map(additionalService -> modelMapperService.forDto()
                .map(additionalService, AdditionalServiceSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult(Messages.ADDITIONALSERVICELIST);

    }

    @Override
    public DataResult<AdditionalService> getById(int rentalAdditionalId) {
        return new SuccessDataResult<AdditionalService>(this.additionalServiceDao.getById(rentalAdditionalId), Messages.ADDITIONALSERVICEFOUND);
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
        Result result = BusinessRules.run(checkIfExistsServiceName(createAdditionalServiceRequest.getServiceName()));

        if (result != null) {
            return result;
        }

        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult(Messages.ADDITIONALSERVICEADD);
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult(Messages.ADDITIONALSERVICEUPDATE);
    }

    @Override
    public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
        this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getServiceId());
        return new SuccessResult(Messages.ADDITIONALSERVICEDELETE);
    }

    private Result checkIfExistsServiceName(String serviceName) {
        if (this.additionalServiceDao.existsByServiceName(serviceName)) {
            return new ErrorResult(Messages.ADDITIONALSERVICENOTFOUND);
        }
        return new SuccessResult();
    }
}
