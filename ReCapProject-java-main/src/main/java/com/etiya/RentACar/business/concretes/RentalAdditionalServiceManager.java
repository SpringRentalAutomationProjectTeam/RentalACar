package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
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
    private LanguageWordService languageWordService;
    @Autowired
    public RentalAdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService,LanguageWordService languageWordService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<AdditionalServiceSearchListDto>> getAll() {
        List<AdditionalService> result = this.additionalServiceDao.findAll();
        List<AdditionalServiceSearchListDto> response = result.stream().map(additionalService -> modelMapperService.forDto()
                .map(additionalService, AdditionalServiceSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult(response,this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICELIST).getData());

    }

    @Override
    public DataResult<AdditionalService> getById(int rentalAdditionalId) {
        Result result = BusinessRules.run(checkIfAdditionalService(rentalAdditionalId));
        if (result!=null){
            return new ErrorDataResult(result);
        }

        return new SuccessDataResult<AdditionalService>(this.additionalServiceDao.getById(rentalAdditionalId),
                this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICEFOUND).getData());
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
        Result result = BusinessRules.run(checkIfServiceNameExists(createAdditionalServiceRequest.getServiceName()));

        if (result != null) {
            return result;
        }

        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICEADD).getData());
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {

        Result result = BusinessRules.run(checkIfServiceNameExists(updateAdditionalServiceRequest.getServiceName()),
                checkIfAdditionalService(updateAdditionalServiceRequest.getServiceId()));
        if (result!=null){
            return result;
        }

        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICEUPDATE).getData());
    }

    @Override
    public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
        Result result = BusinessRules.run(checkIfAdditionalService(deleteAdditionalServiceRequest.getServiceId()));
        if (result!=null){
            return result;
        }

        this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getServiceId());
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICEDELETE).getData());
    }

    public Result checkIfAdditionalService(int additionalServiceId){
        if (!this.additionalServiceDao.existsById(additionalServiceId)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICENOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfServiceNameExists(String serviceName) {
        if (this.additionalServiceDao.existsByServiceName(serviceName)) {
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICENOTFOUND).getData());
        }
        return new SuccessResult();
    }

}
