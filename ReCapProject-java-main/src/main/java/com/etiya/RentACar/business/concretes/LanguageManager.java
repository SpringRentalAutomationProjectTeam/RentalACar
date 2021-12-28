package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.LanguageService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.requests.Language.CreateLanguageRequest;
import com.etiya.RentACar.business.requests.Language.DeleteLanguageRequest;
import com.etiya.RentACar.business.requests.Language.UpdateLanguageRequest;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.LanguageDao;
import com.etiya.RentACar.entites.City;
import com.etiya.RentACar.entites.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageManager implements LanguageService {

    private LanguageDao languageDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public LanguageManager(LanguageDao languageDao, ModelMapperService modelMapperService) {
        this.languageDao = languageDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateLanguageRequest createLanguageRequest) {
        Language language = modelMapperService.forRequest().map(createLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult("Language created");
    }

    @Override
    public Result update(UpdateLanguageRequest updateLanguageRequest) {
        Language language = modelMapperService.forRequest().map(updateLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult("Language updated");
    }

    @Override
    public Result delete(DeleteLanguageRequest deleteLanguageRequest) {
        this.languageDao.deleteById(deleteLanguageRequest.getId());
        return new SuccessResult("Language deleted");
    }
}
