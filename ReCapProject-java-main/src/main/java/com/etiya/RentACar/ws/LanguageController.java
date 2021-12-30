package com.etiya.RentACar.ws;

import com.etiya.RentACar.business.abstracts.LanguageService;
import com.etiya.RentACar.business.abstracts.LanguageWordService;
import com.etiya.RentACar.business.concretes.LanguageManager;
import com.etiya.RentACar.business.dtos.CreditCardDto;
import com.etiya.RentACar.business.dtos.LanguageSearchListDto;
import com.etiya.RentACar.business.requests.Language.CreateLanguageRequest;
import com.etiya.RentACar.business.requests.Language.DeleteLanguageRequest;
import com.etiya.RentACar.business.requests.Language.UpdateLanguageRequest;
import com.etiya.RentACar.business.requests.creditCard.CreateCreditCardRequest;
import com.etiya.RentACar.business.requests.creditCard.DeleteCreditCardRequest;
import com.etiya.RentACar.business.requests.creditCard.UpdateCreditCardRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Color;
import com.etiya.RentACar.entites.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/languages/")
public class LanguageController {

    private LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService){
        this.languageService = languageService;
    }

    @GetMapping("getAll")
    public DataResult<List<LanguageSearchListDto>> getAll() {
        return this.languageService.getAll();
    }

    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateLanguageRequest createLanguageRequest) {
        return this.languageService.add(createLanguageRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Valid UpdateLanguageRequest updateLanguageRequest) {
        return this.languageService.update(updateLanguageRequest);
    }
    @DeleteMapping("delete")
    public Result delete(@RequestBody @Valid DeleteLanguageRequest deleteLanguageRequest) {
        return this.languageService.delete(deleteLanguageRequest);
    }
}
