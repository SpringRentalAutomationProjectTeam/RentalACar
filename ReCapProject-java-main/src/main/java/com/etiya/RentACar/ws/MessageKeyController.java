package com.etiya.RentACar.ws;

import com.etiya.RentACar.business.abstracts.MessageKeyService;
import com.etiya.RentACar.business.dtos.LanguageSearchListDto;
import com.etiya.RentACar.business.dtos.MessageKeySearchListDto;
import com.etiya.RentACar.business.requests.MessageKey.CreateMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.DeleteMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.UpdateMessageKeyRequest;
import com.etiya.RentACar.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/messages/")
public class MessageKeyController {

    private MessageKeyService messageKeyService;

    @Autowired
    public MessageKeyController(MessageKeyService messageKeyService) {
        this.messageKeyService = messageKeyService;
    }

    @GetMapping("getAll")
    public DataResult<List<MessageKeySearchListDto>> getAll() {
        return this.messageKeyService.getAll();
    }

    @PostMapping("add")
    public Result add(@RequestBody CreateMessageKeyRequest createMessageKeyRequest) {
        return this.messageKeyService.add(createMessageKeyRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody UpdateMessageKeyRequest updateMessageKeyRequest) {
        return this.messageKeyService.update(updateMessageKeyRequest);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody DeleteMessageKeyRequest deleteMessageKeyRequest) {
        return this.messageKeyService.delete(deleteMessageKeyRequest);
    }
}
