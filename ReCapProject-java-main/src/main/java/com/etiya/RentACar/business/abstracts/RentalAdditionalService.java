package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.dtos.AdditionalServiceSearchListDto;
import com.etiya.RentACar.business.requests.additionalService.CreateAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.DeleteAdditionalServiceRequest;
import com.etiya.RentACar.business.requests.additionalService.UpdateAdditionalServiceRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.AdditionalService;

import java.util.List;

public interface RentalAdditionalService {
    DataResult<List<AdditionalServiceSearchListDto>> getAll();
    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);
    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);
    Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);
    DataResult<AdditionalService> getById(int rentalAdditionalId);
    Result checkIfAdditionalService(int additionalServiceId);
}
