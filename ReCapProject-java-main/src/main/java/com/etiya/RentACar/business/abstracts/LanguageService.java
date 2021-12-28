package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.requests.Language.CreateLanguageRequest;
import com.etiya.RentACar.business.requests.Language.DeleteLanguageRequest;
import com.etiya.RentACar.business.requests.Language.UpdateLanguageRequest;
import com.etiya.RentACar.core.utilities.results.Result;

public interface LanguageService {

    Result add(CreateLanguageRequest createLanguageRequest);
    Result update(UpdateLanguageRequest updateLanguageRequest);
    Result delete(DeleteLanguageRequest deleteLanguageRequest);

}
