package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.dtos.LanguageSearchListDto;
import com.etiya.RentACar.business.requests.Language.CreateLanguageRequest;
import com.etiya.RentACar.business.requests.Language.DeleteLanguageRequest;
import com.etiya.RentACar.business.requests.Language.UpdateLanguageRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Language;

import java.util.List;

public interface LanguageService {

    Result add(CreateLanguageRequest createLanguageRequest);
    Result update(UpdateLanguageRequest updateLanguageRequest);
    Result delete(DeleteLanguageRequest deleteLanguageRequest);
    DataResult<LanguageSearchListDto> getById(int id);
    DataResult<List<LanguageSearchListDto>> getAll();
    Result checkIfLanguageId(int languaeId);

}
