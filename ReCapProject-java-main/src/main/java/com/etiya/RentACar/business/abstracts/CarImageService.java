package com.etiya.RentACar.business.abstracts;

import java.io.IOException;
import java.util.List;

import com.etiya.RentACar.business.dtos.CarImagesDto;
import com.etiya.RentACar.business.dtos.CarImagesSearchListDto;
import com.etiya.RentACar.business.requests.carImages.CreateCarImageRequest;
import com.etiya.RentACar.business.requests.carImages.DeleteCarImagesRequest;
import com.etiya.RentACar.business.requests.carImages.UpdateCarImageRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.CarImage;

public interface CarImageService {

	DataResult<List<CarImagesSearchListDto>> getAll();

	Result add(CreateCarImageRequest createCarImageRequest) throws IOException;

	Result update(UpdateCarImageRequest updateCarImageRequest) throws IOException;

	Result delete(DeleteCarImagesRequest deleteCarImagesRequest) throws IOException;

	DataResult<List<CarImagesDto>> getCarImageByCarId(int carId);

}
