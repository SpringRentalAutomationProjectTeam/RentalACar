package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.*;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.AdditionalRentalItemSearchListDto;
import com.etiya.RentACar.business.requests.additionalRentalItem.CreateAdditionalRentalItemRequest;
import com.etiya.RentACar.business.requests.additionalRentalItem.DeleteAdditionalRentalItemRequest;
import com.etiya.RentACar.business.requests.additionalRentalItem.UpdateAdditionalRentalItemRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;
import com.etiya.RentACar.dataAccess.abstracts.AdditionalRentalItemDao;
import com.etiya.RentACar.entites.AdditionalRentalItem;
import com.etiya.RentACar.entites.AdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalRentalItemManager implements AdditionalRentalItemService {
    private ModelMapperService modelMapperService;
    private AdditionalRentalItemDao additionalRentalItemDao;
    private RentalService rentalService;
    private RentalAdditionalService rentalAdditionalService;
    private LanguageWordService languageWordService;

    @Autowired
    public AdditionalRentalItemManager(ModelMapperService modelMapperService
            , AdditionalRentalItemDao additionalRentalItemDao, LanguageWordService languageWordService
            ,RentalService rentalService, RentalAdditionalService rentalAdditionalService) {
        this.modelMapperService = modelMapperService;
        this.additionalRentalItemDao = additionalRentalItemDao;
        this.rentalService = rentalService;
        this.languageWordService=languageWordService;
        this.rentalAdditionalService = rentalAdditionalService;
    }


    @Override
    public DataResult<List<AdditionalRentalItemSearchListDto>> getAll() {
        List<AdditionalRentalItem> additionalRentalItems=this.additionalRentalItemDao.findAll();
        List<AdditionalRentalItemSearchListDto> additionalRentalItemSearchListDtos=additionalRentalItems.stream()
                .map(additional-> modelMapperService.forDto().map(additional,AdditionalRentalItemSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(additionalRentalItemSearchListDtos, this.languageWordService.getValueByKey(Messages.ADDITIONALRENTALITEMLIST).getData());

    }

    @Override
    public Result add(CreateAdditionalRentalItemRequest createAdditionalRentalItemRequest) {
        Result result = BusinessRules.run(isAdditionalServiceExists(createAdditionalRentalItemRequest.getAdditionalServiceId()),
                isRentalExists(createAdditionalRentalItemRequest.getRentalId()));
        if (result!=null){
            return result;
        }
        AdditionalRentalItem additionalRentalItem=modelMapperService.forRequest().map(createAdditionalRentalItemRequest,AdditionalRentalItem.class);
        this.additionalRentalItemDao.save(additionalRentalItem);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.ADDITIONALRENTALITEMADD).getData());
    }

    @Override
    public Result delete(DeleteAdditionalRentalItemRequest deleteAdditionalRentalItemRequest) {
        Result result = BusinessRules.run(isAdditionalRentalItemExists(deleteAdditionalRentalItemRequest.getId()));
        if (result!=null){
            return result;
        }
        AdditionalRentalItem additionalRentalItem = modelMapperService.forRequest()
                .map(deleteAdditionalRentalItemRequest, AdditionalRentalItem.class);
        this.additionalRentalItemDao.delete(additionalRentalItem);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.ADDITIONALRENTALITEMDELETE).getData());
    }

    @Override
    public Result update(UpdateAdditionalRentalItemRequest updateAdditionalRentalItemRequest) {
        Result result = BusinessRules.run(isAdditionalRentalItemExists(updateAdditionalRentalItemRequest.getId()),
                isAdditionalServiceExists(updateAdditionalRentalItemRequest.getAdditionalServiceId()),
                isRentalExists(updateAdditionalRentalItemRequest.getRentalId()));
        if (result!=null){
            return result;
        }
        AdditionalRentalItem additionalRentalItem = modelMapperService.forRequest()
                .map(updateAdditionalRentalItemRequest, AdditionalRentalItem.class);
        this.additionalRentalItemDao.save(additionalRentalItem);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.ADDITIONALRENTALITEMUPDATE).getData());
    }

    @Override
    public DataResult<List<AdditionalRentalItemSearchListDto>> getByRentalId(int rentalId) {
        Result businessResult = BusinessRules.run(isRentalExists(rentalId));
        if (businessResult!=null){
            return new ErrorDataResult(businessResult);
        }
        List<AdditionalRentalItem> request = this.additionalRentalItemDao.getByRentalRentalId(rentalId);
        List<AdditionalRentalItemSearchListDto> response = request.stream()
                .map(additionalRentalItem -> modelMapperService.forDto()
                        .map(additionalRentalItem , AdditionalRentalItemSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<AdditionalRentalItemSearchListDto>>(response);
    }



    private Result isRentalExists(int rentalId){

        if (!this.rentalService.checkIfRentalExists(rentalId).isSuccess()){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.RENTALNOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result isAdditionalServiceExists(int id){

        if (!this.rentalAdditionalService.checkIfAdditionalService(id).isSuccess()){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.ADDITIONALSERVICENOTFOUND).getData());
        }
        return new SuccessResult();
    }

    private Result isAdditionalRentalItemExists(int id){

        if (!this.additionalRentalItemDao.existsById(id)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.ADDITIONALRENTALITEMNOTFOUND).getData());
        }
        return new SuccessResult();
    }
}
