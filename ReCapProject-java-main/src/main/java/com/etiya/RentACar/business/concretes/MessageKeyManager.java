package com.etiya.RentACar.business.concretes;

import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.business.requests.MessageKey.CreateMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.DeleteMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.UpdateMessageKeyRequest;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.MessageKeyDao;
import com.etiya.RentACar.entites.MessageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageKeyManager implements MessageKeyService {

    private MessageKeyDao messageKeyDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public MessageKeyManager(MessageKeyDao messageKeyDao,ModelMapperService modelMapperService) {
        this.messageKeyDao = messageKeyDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateMessageKeyRequest createMessageKeyRequest) {
       MessageKey messageKey = modelMapperService.forRequest().map(createMessageKeyRequest,MessageKey.class);
       this.messageKeyDao.save(messageKey);
       return new SuccessResult("message eklendi");
    }

    @Override
    public Result update(UpdateMessageKeyRequest updateMessageKeyRequest) {

        MessageKey messageKey = modelMapperService.forRequest().map(updateMessageKeyRequest, MessageKey.class);
        this.messageKeyDao.save(messageKey);
        return new SuccessResult("Message key updated");
    }

    @Override
    public Result delete(DeleteMessageKeyRequest deleteMessageKeyRequest) {
        MessageKey messageKey = modelMapperService.forRequest().map(deleteMessageKeyRequest,MessageKey.class);
        this.messageKeyDao.delete(messageKey);
        return new SuccessResult("message deleted");
    }

    @Override
    public DataResult<MessageKey> getByKey(String key) {
        return new SuccessDataResult<MessageKey>(this.messageKeyDao.findByKey(key));
    }
}
