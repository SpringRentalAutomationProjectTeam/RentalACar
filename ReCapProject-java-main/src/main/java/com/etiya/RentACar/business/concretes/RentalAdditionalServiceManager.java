package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.RentalAdditionalService;
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
                .map(additionalService,AdditionalServiceSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult(response);

    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
        Result result = BusinessRules.run(checkExistsServiceName(createAdditionalServiceRequest.getServiceName()));

        if (result!=null){
            return result;
        }

        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("Ek hizmet eklendi");
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("Ek hizmet gümcellendi");
    }

    @Override
    public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
        this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getServiceId());
        return new SuccessResult("Ek hizmet silindi.");
    }

    private Result checkExistsServiceName(String serviceName){
        if(this.additionalServiceDao.existsByServiceName(serviceName)){
            return new ErrorResult("Bu Service bulunmaktadır.");
        }
        return new SuccessResult();
    }
}
