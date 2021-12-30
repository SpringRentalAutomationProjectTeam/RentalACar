package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.LanguageService;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.LanguageWordSearchListDto;
import com.etiya.RentACar.business.requests.LanguageWord.CreateLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.DeleteLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.UpdateLanguageWordRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;

import com.etiya.RentACar.dataAccess.abstracts.LanguageWordDao;
import com.etiya.RentACar.entites.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageWordManager implements LanguageWordService {
    private LanguageWordDao languageWordDao;
    private ModelMapperService modelMapperService;
    private MessageKeyService messageKeyService;
    private LanguageService languageService;
    private Environment environment;
    private int languageId = 2;


    @Autowired
    public LanguageWordManager(LanguageWordDao languageWordDao, ModelMapperService modelMapperService, @Lazy MessageKeyService messageKeyService,
                               @Lazy LanguageService languageService, Environment environment) {
        this.languageWordDao = languageWordDao;
        this.modelMapperService = modelMapperService;
        this.messageKeyService = messageKeyService;
        this.languageService = languageService;
        this.environment = environment;
    }

    @Override
    public DataResult<List<LanguageWordSearchListDto>> getAll() {


        List<LanguageWord> result = this.languageWordDao.findAll();
        List<LanguageWordSearchListDto> response = result.stream().map(languageWord -> modelMapperService.forDto()
                .map(languageWord, LanguageWordSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<LanguageWordSearchListDto>>(response, getValueByKey(Messages.LANGUAGEWORDLIST).getData());
    }


    @Override
    public Result add(CreateLanguageWordRequest createLanguageWordRequest) {
        Result result = BusinessRules.run(checkIfLanguageWordNameExists(createLanguageWordRequest.getTranslation()));
        if (result != null) {
            return result;
        }

        LanguageWord languageWord = this.modelMapperService.forRequest().map(createLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
        return new SuccessResult(getValueByKey(Messages.LANGUAGEWORDADD).getData());

    }

    @Override
    public Result update(UpdateLanguageWordRequest updateLanguageWordRequest) {
        Result result = BusinessRules.run(checkIfLanguageWord(updateLanguageWordRequest.getId()),
                checkIfLanguageWordNameExists(updateLanguageWordRequest.getTranslation()));
        if (result != null) {
            return result;
        }

        LanguageWord languageWord = this.modelMapperService.forRequest().map(updateLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
        return new SuccessResult(getValueByKey(Messages.LANGUAGEWORDUPDATE).getData());
    }

    @Override
    public Result delete(DeleteLanguageWordRequest deleteLanguageWordRequest) {
        Result result = BusinessRules.run(checkIfLanguageWord(deleteLanguageWordRequest.getId()));
        if (result != null) {
            return result;
        }

        LanguageWord languageWord = this.modelMapperService.forRequest().map(deleteLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.delete(languageWord);
        return new SuccessResult(getValueByKey(Messages.LANGUAGEWORDDELETE).getData());
    }

    @Override
    public DataResult<String> getValueByKey(String key) { //key messakey ıd languece value

        if (!this.messageKeyService.checkIfMessageKeyNameNotExists(key).isSuccess()) {

            return new SuccessDataResult<String>(key);
        }
        String message = findByLanguageIdAndKeyId(this.messageKeyService.getByKey(key).getData().getId()).getData();
        return new SuccessDataResult<String>(message);
    }

    private DataResult<String> findByLanguageIdAndKeyId(int keyId) {
        checkIfIsThereLanguage(this.languageId);
        if (!this.languageWordDao.existsByLanguageIdAndMessageKeyId(this.languageId, keyId)) {
            return new SuccessDataResult<String>(checkIfDefaultLanguageAndValueExists(keyId).getData());
        }
        String words = this.languageWordDao.getByLanguageIdAndMessageKeyId(this.languageId, keyId).getTranslation();
        return new SuccessDataResult<String>(words);
    }

    private DataResult<String> checkIfDefaultLanguageAndValueExists(int keyId) {
        int default_language_id = Integer.parseInt(this.environment.getProperty("message.languageId"));
        if (!this.languageWordDao.existsByLanguageIdAndMessageKeyId(default_language_id, keyId)) {
            int default_key = this.messageKeyService.getByKey(Messages.DEFAULTKEY).getData().getId();
            String default_word = this.languageWordDao.getByLanguageIdAndMessageKeyId(default_language_id,
                    default_key).getTranslation();
            return new SuccessDataResult<String>(default_word);
        } else {
            String default_language_word = this.languageWordDao.getByLanguageIdAndMessageKeyId(default_language_id, keyId).getTranslation();
            return new SuccessDataResult<String>(default_language_word);
        }
    }

    private void checkIfIsThereLanguage(int languageId) {
        if (!this.languageService.checkIfLanguageId(languageId).isSuccess()) {
            this.languageId = Integer.parseInt(this.environment.getProperty("message.languageId"));
        }
    }


    private Result checkIfLanguageWordNameExists(String languageWord) {
        if (this.languageWordDao.existsByTranslation(languageWord)) {
            return new ErrorResult(getValueByKey(Messages.LANGUAGEWORDNAMEERROR).getData());
        }
        return new SuccessResult();
    }

    private Result checkIfLanguageWord(int messageKeyId) {
        if (!this.languageWordDao.existsById(messageKeyId)) {
            return new ErrorResult(getValueByKey(Messages.LANGUAGEWORDNOTFOUND).getData());
        }
        return new SuccessResult();

    }
}
