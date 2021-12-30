package com.etiya.RentACar.ws;


import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.dtos.LanguageSearchListDto;
import com.etiya.RentACar.business.dtos.LanguageWordSearchListDto;
import com.etiya.RentACar.business.requests.LanguageWord.CreateLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.DeleteLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.UpdateLanguageWordRequest;
import com.etiya.RentACar.business.requests.MessageKey.CreateMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.DeleteMessageKeyRequest;
import com.etiya.RentACar.business.requests.MessageKey.UpdateMessageKeyRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/languageWords/")
public class LanguageWordController {

    private LanguageWordService languageWordService;

    @Autowired
    public LanguageWordController(LanguageWordService languageWordService) {
        this.languageWordService = languageWordService;
    }

    @GetMapping("getAll")
    public DataResult<List<LanguageWordSearchListDto>> getAll() {
        return this.languageWordService.getAll();
    }

    @PostMapping("add")
    public Result add(@RequestBody CreateLanguageWordRequest createLanguageWordRequest) {
        return this.languageWordService.add(createLanguageWordRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody UpdateLanguageWordRequest updateLanguageWordRequest) {
        return this.languageWordService.update(updateLanguageWordRequest);
    }

    @DeleteMapping("delete")
    public Result delete(@RequestBody DeleteLanguageWordRequest deleteLanguageWordRequest) {
        return this.languageWordService.delete(deleteLanguageWordRequest);
    }
}
