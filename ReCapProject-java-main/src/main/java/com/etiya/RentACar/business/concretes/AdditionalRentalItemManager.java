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
        return new SuccessDataResult<>(additionalRentalItemSearchListDtos, this.languageWordService.getValueByKey("additional_rental_item_list").getData());

    }

    @Override
    public Result add(CreateAdditionalRentalItemRequest createAdditionalRentalItemRequest) {
        var result = BusinessRules.run(isAdditionalServiceExists(createAdditionalRentalItemRequest.getAdditionalServiceId()),
                isRentalExists(createAdditionalRentalItemRequest.getRentalId()));
        if (result!=null){
            return result;
        }
        AdditionalRentalItem additionalRentalItem=modelMapperService.forRequest().map(createAdditionalRentalItemRequest,AdditionalRentalItem.class);
        this.additionalRentalItemDao.save(additionalRentalItem);
        return new SuccessResult(this.languageWordService.getValueByKey("additional_rental_item_add").getData());
    }

    @Override
    public Result delete(DeleteAdditionalRentalItemRequest deleteAdditionalRentalItemRequest) {
        var result = BusinessRules.run(isAdditionalRentalItemExists(deleteAdditionalRentalItemRequest.getId()));
        if (result!=null){
            return result;
        }
        AdditionalRentalItem additionalRentalItem = modelMapperService.forRequest()
                .map(deleteAdditionalRentalItemRequest, AdditionalRentalItem.class);
        this.additionalRentalItemDao.delete(additionalRentalItem);
        return new SuccessResult(this.languageWordService.getValueByKey("additional_rental_item_delete").getData());
    }

    @Override
    public Result update(UpdateAdditionalRentalItemRequest updateAdditionalRentalItemRequest) {
        var result = BusinessRules.run(isAdditionalRentalItemExists(updateAdditionalRentalItemRequest.getId()),
                isAdditionalServiceExists(updateAdditionalRentalItemRequest.getAdditionalServiceId()),
                isRentalExists(updateAdditionalRentalItemRequest.getRentalId()));
        if (result!=null){
            return result;
        }
        AdditionalRentalItem additionalRentalItem = modelMapperService.forRequest()
                .map(updateAdditionalRentalItemRequest, AdditionalRentalItem.class);
        this.additionalRentalItemDao.save(additionalRentalItem);
        return new SuccessResult(this.languageWordService.getValueByKey("additional_rental_item_update").getData());
    }

    @Override
    public DataResult<List<AdditionalRentalItemSearchListDto>> getByRentalId(int rentalId) {
        var businessResult = BusinessRules.run(isRentalExists(rentalId));
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
        var result = this.rentalService.checkIfRentalExists(rentalId);
        if (!result.isSuccess()){
            return new ErrorResult(this.languageWordService.getValueByKey("rental_not_found").getData());
        }
        return new SuccessResult();
    }

    private Result isAdditionalServiceExists(int id){
        var result = this.rentalAdditionalService.checkIfAdditionalService(id);
        if (!result.isSuccess()){
            return new ErrorResult(this.languageWordService.getValueByKey("additionalservice_not_found").getData());
        }
        return new SuccessResult();
    }

    private Result isAdditionalRentalItemExists(int id){

        var result = this.additionalRentalItemDao.existsById(id);
        if (!result){
            return new ErrorResult(this.languageWordService.getValueByKey("additional_rental_not_found").getData());
        }
        return new SuccessResult();
    }
}
