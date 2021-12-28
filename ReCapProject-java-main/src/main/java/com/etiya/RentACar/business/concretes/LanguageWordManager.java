package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.requests.LanguageWord.CreateLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.DeleteLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.UpdateLanguageWordRequest;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;

import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.LanguageDao;
import com.etiya.RentACar.dataAccess.abstracts.LanguageWordDao;
import com.etiya.RentACar.entites.Brand;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.LanguageWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LanguageWordManager implements LanguageWordService {
    private LanguageWordDao languageWordDao;
    private ModelMapperService modelMapperService;
    private MessageKeyService messageKeyService;
    @Value("${message.languageId}")
    private int languageId;


    @Autowired
    public LanguageWordManager(LanguageWordDao languageWordDao, ModelMapperService modelMapperService,MessageKeyService messageKeyService) {
        this.languageWordDao = languageWordDao;
        this.modelMapperService = modelMapperService;
        this.messageKeyService = messageKeyService;
    }


    @Override
    public Result add(CreateLanguageWordRequest createLanguageWordRequest) {
        LanguageWord languageWord = this.modelMapperService.forRequest().map(createLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
        return new SuccessResult("language added");


    }

    @Override
    public Result update(UpdateLanguageWordRequest updateLanguageWordRequest) {
        LanguageWord languageWord = this.modelMapperService.forRequest().map(updateLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
        return new SuccessResult("language updated");
    }

    @Override
    public Result delete(DeleteLanguageWordRequest deleteLanguageWordRequest) {
        LanguageWord languageWord = this.modelMapperService.forRequest().map(deleteLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.delete(languageWord);
        return new SuccessResult("language deleted");
    }

    @Override
    public DataResult<String> getValueByKey(String key) { //key messakey ıd languece value
        return new SuccessDataResult<String>(findByLanguageIdAndKeyId(this.messageKeyService.getByKey(key).getData().getId()).getData().getTranslation());
    }

    private DataResult<LanguageWord> findByLanguageIdAndKeyId(int keyId) {
       LanguageWord words= this.languageWordDao.getByLanguageIdAndMessageKeyId(languageId,keyId);
        return new SuccessDataResult<LanguageWord>(words);
    }
}
