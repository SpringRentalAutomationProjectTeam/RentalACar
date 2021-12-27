package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.dtos.AdditionalRentalItemSearchListDto;
import com.etiya.RentACar.business.requests.additionalRentalItem.CreateAdditionalRentalItemRequest;
import com.etiya.RentACar.business.requests.additionalRentalItem.DeleteAdditionalRentalItemRequest;
import com.etiya.RentACar.business.requests.additionalRentalItem.UpdateAdditionalRentalItemRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

import java.util.List;

public interface AdditionalRentalItemService {
    DataResult<List<AdditionalRentalItemSearchListDto>> getAll();
    Result add(CreateAdditionalRentalItemRequest createAdditionalRentalItemRequest);
    Result delete(DeleteAdditionalRentalItemRequest deleteAdditionalRentalItemRequest);
    Result update(UpdateAdditionalRentalItemRequest updateAdditionalRentalItemRequest);
    DataResult<List<AdditionalRentalItemSearchListDto>> getByRentalId(int rentalId);
}
