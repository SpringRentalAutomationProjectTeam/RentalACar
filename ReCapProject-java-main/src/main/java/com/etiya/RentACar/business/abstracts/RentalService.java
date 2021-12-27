package com.etiya.RentACar.business.abstracts;

import java.util.List;

import com.etiya.RentACar.business.dtos.RentalSearchListDto;
import com.etiya.RentACar.business.requests.Rental.CreateRentalRequest;
import com.etiya.RentACar.business.requests.Rental.DeleteRentalRequest;
import com.etiya.RentACar.business.requests.Rental.UpdateRentalRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

public interface RentalService {
	DataResult<List<RentalSearchListDto>> getAll();
	Result add(CreateRentalRequest createRentalRequest);
	Result delete(DeleteRentalRequest deleteRentalRequest);
	Result update(UpdateRentalRequest updateRentalRequest);
	Result checkIfCarIsReturned(int carId);
	DataResult<RentalSearchListDto> getByRentalId(int rentalId);
	Result checkIfRentalExists(int rentalId);

}
