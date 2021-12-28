package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.requests.MessageKey.CreateMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.DeleteMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.UpdateMessageKeyRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.MessageKey;

public interface MessageKeyService {
    Result add(CreateMessageKeyRequest createMessageKeyRequest);
    Result update(UpdateMessageKeyRequest updateMessageKeyRequest );
    Result delete(DeleteMessageKeyRequest deleteMessageKeyRequest);

    DataResult<MessageKey> getByKey(String key);

}
