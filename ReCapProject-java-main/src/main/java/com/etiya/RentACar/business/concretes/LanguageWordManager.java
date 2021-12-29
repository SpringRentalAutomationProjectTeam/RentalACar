package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.LanguageService;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.MessageKeyService;
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
    private int languageId=2;



    @Autowired
    public LanguageWordManager(LanguageWordDao languageWordDao, ModelMapperService modelMapperService,@Lazy MessageKeyService messageKeyService,
                              @Lazy LanguageService languageService,Environment environment) {
        this.languageWordDao = languageWordDao;
        this.modelMapperService = modelMapperService;
        this.messageKeyService = messageKeyService;
        this.languageService=languageService;
        this.environment=environment;
    }


    @Override
    public DataResult<List<LanguageWordSearchListDto>> getAll() {


        List<LanguageWord> result  = this.languageWordDao.findAll();
        List<LanguageWordSearchListDto> response = result.stream().map(languageWord -> modelMapperService.forDto()
                .map(languageWord, LanguageWordSearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<LanguageWordSearchListDto>>(response,getValueByKey("languageword_list").getData());
    }


    @Override
    public Result add(CreateLanguageWordRequest createLanguageWordRequest) {
        Result result = BusinessRules.run(checkIfLanguageWordNameExists(createLanguageWordRequest.getTranslation()));
        if (result != null) {
            return result;
        }

        LanguageWord languageWord = this.modelMapperService.forRequest().map(createLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
        return new SuccessResult(getValueByKey("languageword_add").getData());

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
        return new SuccessResult(getValueByKey("languageword_update").getData());
    }

    @Override
    public Result delete(DeleteLanguageWordRequest deleteLanguageWordRequest) {
        Result result = BusinessRules.run(checkIfLanguageWord(deleteLanguageWordRequest.getId()));
        if (result != null) {
            return result;
        }

        LanguageWord languageWord = this.modelMapperService.forRequest().map(deleteLanguageWordRequest, LanguageWord.class);
        this.languageWordDao.delete(languageWord);
        return new SuccessResult(getValueByKey("languageword_delete").getData());
    }

    @Override
    public DataResult<String> getValueByKey(String key) { //key messakey ıd languece value

        if (!this.messageKeyService.checkIfMessageKeyNameNotExists(key).isSuccess()){

            return new SuccessDataResult<String>(key);
        }
        String  word = findByLanguageIdAndKeyId(this.messageKeyService.getByKey(key).getData().getId()).getData();
        return new SuccessDataResult<String>(word);
    }
//

    private DataResult<String> findByLanguageIdAndKeyId(int keyId){
        checkIfIsThereLanguage(this.languageId);
        if (!this.languageWordDao.existsByLanguageIdAndMessageKeyId(this.languageId,keyId)){
            int default_language_id= Integer.parseInt(this.environment.getProperty("message.languageId"));
            if (!this.languageWordDao.existsByLanguageIdAndMessageKeyId(default_language_id,keyId)){
                int a =this.messageKeyService.getByKey("default_key").getData().getId();
                String  word =this.languageWordDao.getByLanguageIdAndMessageKeyId(this.languageId,
                        a).getTranslation();
                return new SuccessDataResult<String>(word);
            }
            else{
                String word1=this.languageWordDao.getByLanguageIdAndMessageKeyId(default_language_id,keyId).getTranslation();
                return  new SuccessDataResult<String>(word1);
            }

        }
        String  words= this.languageWordDao.getByLanguageIdAndMessageKeyId(this.languageId,keyId).getTranslation();
        return new SuccessDataResult<String>(words);
    }

  /*  private DataResult<LanguageWord> findByLanguageIdAndKeyId(int keyId) {

       checkIfIsThereLanguage(this.languageId);
        //exists eklemek zorunda mıyız
        if (!this.languageWordDao.existsByLanguageIdAndMessageKeyId(this.languageId,keyId)){//value yok
            this.languageId= Integer.parseInt(this.environment.getProperty("message.languageId"));
           int b= checkLanguageWord(languageId,keyId).getData();
           LanguageWord word1=this.languageWordDao.getByLanguageIdAndMessageKeyId(this.languageId,b);
          return new SuccessDataResult<>(word1);

        }else{
        LanguageWord words= this.languageWordDao.getByLanguageIdAndMessageKeyId(this.languageId,keyId);
        return new SuccessDataResult<LanguageWord>(words);
        }

    }*/

    private void checkIfIsThereLanguage(int languageId){
        if (!this.languageService.checkIfLanguageId(languageId).isSuccess()) {
            this.languageId = Integer.parseInt(this.environment.getProperty("message.languageId"));
        }
    }

                    /**önce default_language ye bakıyor varsa defult_language de message yazıyor yoksa
                     * default_message olan default mesage yazıyor **/
    private DataResult<Integer> checkLanguageWord(int default_language,int keyId){//value varmı yoksa defaullanguage value varmı yoksa default messages
        if (!this.languageWordDao.existsByLanguageIdAndMessageKeyId(default_language , keyId)) {
            int a =this.messageKeyService.getByKey("default_key").getData().getId();

            return new SuccessDataResult<Integer>(a);
        }
            return new SuccessDataResult<Integer>(keyId);
    }


    private Result checkIfLanguageWordNameExists(String languageWord){
        if (this.languageWordDao.existsByTranslation(languageWord)){
            return new ErrorResult(getValueByKey("languageword_name_error").getData());
        }
        return new SuccessResult();
    }

    private Result checkIfLanguageWord(int messageKeyId){
        if (!this.languageWordDao.existsById(messageKeyId)){
            return new ErrorResult(getValueByKey("languageword_not_found").getData());
        }
        return new SuccessResult();

    }
}
