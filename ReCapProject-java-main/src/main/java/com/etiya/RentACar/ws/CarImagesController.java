package com.etiya.RentACar.ws;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.RentACar.business.abstracts.CarImageService;
import com.etiya.RentACar.business.dtos.CarImagesDto;
import com.etiya.RentACar.business.dtos.CarImagesSearchListDto;
import com.etiya.RentACar.business.requests.carImages.CreateCarImageRequest;
import com.etiya.RentACar.business.requests.carImages.DeleteCarImagesRequest;
import com.etiya.RentACar.business.requests.carImages.UpdateCarImageRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.CarImage;

@RestController
@RequestMapping("api/carImages/")
public class CarImagesController {

	private CarImageService carImageService;

	@Autowired
	private CarImagesController(CarImageService carImageService) {
		super();
		this.carImageService = carImageService;
	}

	@GetMapping("getAll")
	public DataResult<List<CarImagesSearchListDto>> getAll() {
		return this.carImageService.getAll();
	}

	@GetMapping("getCarImagesByCarId")
	public DataResult<List<CarImagesDto>> getCarImagesByCarId(int carId) {
		return this.carImageService.getCarImageByCarId(carId);
	}

	@PostMapping("add")
	public Result add(@RequestParam("carId") int carId, MultipartFile file) throws IOException {
		CreateCarImageRequest createCarImageRequest = new CreateCarImageRequest();
		createCarImageRequest.setCarId(carId);
		createCarImageRequest.setFile(file);
		return this.carImageService.add(createCarImageRequest);
	}

	@PutMapping("update")
	public Result update(@RequestParam("imageId") int imageId, @RequestParam("carId") int carId, MultipartFile file)
			throws IOException {
		UpdateCarImageRequest updateCarImageRequest = new UpdateCarImageRequest();
		updateCarImageRequest.setImageId(imageId);
		updateCarImageRequest.setCarId(carId);
		updateCarImageRequest.setFile(file);
		return this.carImageService.update(updateCarImageRequest);
	}

	@DeleteMapping("delete")
	public Result delete(@RequestBody DeleteCarImagesRequest deleteCarImagesRequest) throws IOException {
		return this.carImageService.delete(deleteCarImagesRequest);
	}

}
