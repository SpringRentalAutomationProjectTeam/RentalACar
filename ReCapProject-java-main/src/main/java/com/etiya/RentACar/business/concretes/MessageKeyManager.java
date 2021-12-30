package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.CarDetailDto;
import com.etiya.RentACar.business.dtos.LanguageSearchListDto;
import com.etiya.RentACar.business.dtos.MessageKeySearchListDto;
import com.etiya.RentACar.business.requests.MessageKey.CreateMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.DeleteMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.UpdateMessageKeyRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.*;
import com.etiya.RentACar.dataAccess.abstracts.MessageKeyDao;
import com.etiya.RentACar.entites.Language;
import com.etiya.RentACar.entites.MessageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageKeyManager implements MessageKeyService {

    private MessageKeyDao messageKeyDao;
    private ModelMapperService modelMapperService;
    private LanguageWordService languageWordService;

    @Autowired
    public MessageKeyManager(MessageKeyDao messageKeyDao,ModelMapperService modelMapperService,LanguageWordService languageWordService) {
        this.messageKeyDao = messageKeyDao;
        this.modelMapperService = modelMapperService;
        this.languageWordService=languageWordService;
    }


    @Override
    public DataResult<List<MessageKeySearchListDto>> getAll() {
        List<MessageKey> result  = this.messageKeyDao.findAll();
        List<MessageKeySearchListDto> response = result.stream().map(messageKey -> modelMapperService.forDto()
                .map(messageKey, MessageKeySearchListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<MessageKeySearchListDto>>(response,this.languageWordService.getValueByKey(Messages.MESSAGEKEYLIST).getData());
    }

    @Override
    public Result add(CreateMessageKeyRequest createMessageKeyRequest) {
        Result result = BusinessRules.run(checkIfMessageKeyNameExists(createMessageKeyRequest.getKey()));
        if (result != null) {
            return result;
        }

       MessageKey messageKey = modelMapperService.forRequest().map(createMessageKeyRequest,MessageKey.class);
       this.messageKeyDao.save(messageKey);
       return new SuccessResult(this.languageWordService.getValueByKey(Messages.MESSAGEKEYADD).getData());
    }

    @Override
    public Result update(UpdateMessageKeyRequest updateMessageKeyRequest) {
        Result result = BusinessRules.run(checkIfMessageKey(updateMessageKeyRequest.getId()),
                checkIfMessageKeyNameExists(updateMessageKeyRequest.getKey()));
        if (result != null) {
            return result;
        }


        MessageKey messageKey = modelMapperService.forRequest().map(updateMessageKeyRequest, MessageKey.class);
        this.messageKeyDao.save(messageKey);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.MESSAGEKEYUPDATE).getData());
    }

    @Override
    public Result delete(DeleteMessageKeyRequest deleteMessageKeyRequest) {
        Result result = BusinessRules.run(checkIfMessageKey(deleteMessageKeyRequest.getId()));
        if (result != null) {
            return result;
        }

        MessageKey messageKey = modelMapperService.forRequest().map(deleteMessageKeyRequest,MessageKey.class);
        this.messageKeyDao.delete(messageKey);
        return new SuccessResult(this.languageWordService.getValueByKey(Messages.MESSAGEKEYDELETE).getData());
    }



    @Override
    public DataResult<MessageKey> getByKey(String key) {
        return new SuccessDataResult<MessageKey>(this.messageKeyDao.findByKey(key));
    }

    private Result checkIfMessageKeyNameExists(String messageKey){
        if (this.messageKeyDao.existsByKey(messageKey)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.MESSAGEKEYNAMEERROR).getData());
        }
        return new SuccessResult();
    }


    @Override
    public Result checkIfMessageKeyNameNotExists(String messageKey){
        if (!this.messageKeyDao.existsByKey(messageKey)){
            return new ErrorResult();
        }
        return new SuccessResult();
    }

    private Result checkIfMessageKey(int messageKeyId){
        if (!this.messageKeyDao.existsById(messageKeyId)){
            return new ErrorResult(this.languageWordService.getValueByKey(Messages.MESSAGEKEYNOTFOUND).getData());
        }
        return new SuccessResult();

    }
}
