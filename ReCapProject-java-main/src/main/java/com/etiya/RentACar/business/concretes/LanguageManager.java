package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.LanguageService;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.CarDetailDto;
import com.etiya.RentACar.business.dtos.CitySearchListDto;
import com.etiya.RentACar.business.dtos.LanguageSearchListDto;
import com.etiya.RentACar.business.requests.Language.CreateLanguageRequest;
import com.etiya.RentACar.business.requests.Language.DeleteLanguageRequest;
import com.etiya.RentACar.business.requests.Language.UpdateLanguageRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;
import com.etiya.RentACar.dataAccess.abstracts.LanguageDao;
import com.etiya.RentACar.entites.City;
import com.etiya.RentACar.entites.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageManager implements LanguageService {

    private LanguageDao languageDao;
    private ModelMapperService modelMapperService;
    private LanguageWordService languageWordService;

    @Autowired
    public LanguageManager(LanguageDao languageDao, ModelMapperService modelMapperService, LanguageWordService languageWordService) {
        this.languageDao = languageDao;
        this.modelMapperService = modelMapperService;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<LanguageSearchListDto>> getAll() {
        List<Language> languages  = this.languageDao.findAll();
        List<LanguageSearchListDto> response = languages.stream().map(language -> modelMapperService.forDto()
                .map(language, LanguageSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<LanguageSearchListDto>>(response,this.languageWordService.getValueByKey(Messages.LANGUAGELIST).getData());
    }

    @Override
    public Result add(CreateLanguageRequest createLanguageRequest) {
        Result result = BusinessRules.run(checkIfLanguageNameExists(createLanguageRequest.getLanguageName()));
        if (result != null) {
            return result;
        }
        Language language = modelMapperService.forRequest().map(createLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.LANGUAGEADD).getData());
    }

    @Override
    public Result update(UpdateLanguageRequest updateLanguageRequest) {
        Result result = BusinessRules.run(checkIfLanguageNameExists(updateLanguageRequest.getLanguageName()),
                checkIfLanguageId(updateLanguageRequest.getId()));
        if (result != null) {
            return result;
        }
        Language language = modelMapperService.forRequest().map(updateLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.LANGUAGEUPDATE).getData());
    }

    @Override
    public Result delete(DeleteLanguageRequest deleteLanguageRequest) {
        Result result = BusinessRules.run(checkIfLanguageId(deleteLanguageRequest.getId()));
        if (result != null) {
            return result;
        }
        this.languageDao.deleteById(deleteLanguageRequest.getId());
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.LANGUAGEDELETE).getData());
    }

    @Override
    public DataResult<LanguageSearchListDto> getById(int id) {
        Result result = BusinessRules.run(checkIfLanguageId(id));
        if (result != null) {
            return null;
        }
        return new SuccessDataResult<LanguageSearchListDto>(this.languageDao.getById(id));
    }

    private Result checkIfLanguageNameExists(String languageName){
        if (this.languageDao.existsByLanguageName(languageName)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.LANGUAGENAMEERROR).getData());
        }
        return new SuccessResult();
    }
    @Override
    public Result checkIfLanguageId(int languageId){
        if (!this.languageDao.existsById(languageId)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.LANGUAGENOTFOUND).getData());
        }
        return new SuccessResult();

    }
}
