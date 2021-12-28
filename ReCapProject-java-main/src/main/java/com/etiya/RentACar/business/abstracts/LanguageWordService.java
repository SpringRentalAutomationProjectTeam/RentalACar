package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.dtos.LanguageWordSearchListDto;
import com.etiya.RentACar.business.requests.LanguageWord.CreateLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.DeleteLanguageWordRequest;
import com.etiya.RentACar.business.requests.LanguageWord.UpdateLanguageWordRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.LanguageWord;

import java.util.List;

public interface LanguageWordService {

    Result add(CreateLanguageWordRequest createLanguageWordRequest);
    Result update(UpdateLanguageWordRequest updateLanguageWordRequest);
    Result delete(DeleteLanguageWordRequest deleteLanguageWordRequest);
    DataResult<List<LanguageWordSearchListDto>> getAll();
    DataResult<String> getValueByKey(String key);

}
