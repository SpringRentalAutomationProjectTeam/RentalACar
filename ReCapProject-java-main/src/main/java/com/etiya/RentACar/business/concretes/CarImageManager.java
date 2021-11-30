package com.etiya.RentACar.business.concretes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.RentACar.business.abstracts.CarImageService;
import com.etiya.RentACar.business.abstracts.CarService;
import com.etiya.RentACar.business.constants.FilePathConfiguration;
import com.etiya.RentACar.business.constants.Messages;
import com.etiya.RentACar.business.dtos.CarImagesDto;
import com.etiya.RentACar.business.dtos.CarImagesSearchListDto;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.requests.carImages.CreateCarImageRequest;
import com.etiya.RentACar.business.requests.carImages.DeleteCarImagesRequest;
import com.etiya.RentACar.business.requests.carImages.UpdateCarImageRequest;
import com.etiya.RentACar.core.utilities.business.BusinessRules;
import com.etiya.RentACar.core.utilities.helpers.FileHelper;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorDataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.CarImageDao;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.CarImage;

@Service
public class CarImageManager implements CarImageService {

	private CarImageDao carImageDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private FileHelper fileHelper;

	@Autowired
	private CarImageManager(CarImageDao carImageDao, ModelMapperService modelMapperService,
			@Lazy CarService carService, FileHelper fileHelper) {
		super();
		this.carImageDao = carImageDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.fileHelper = fileHelper;
	}

	@Override
	public DataResult<List<CarImagesSearchListDto>> getAll() {
		List<CarImage> carImages = this.carImageDao.findAll();
		List<CarImagesSearchListDto> result = carImages.stream()
				.map(carImage -> modelMapperService.forDto().map(carImage, CarImagesSearchListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarImagesSearchListDto>>(result, "Araba resimleri listelendi");
	}

	@Override
	public Result add(CreateCarImageRequest createCarImageRequest) throws IOException {
		Result result = BusinessRules.run(existsByCar(createCarImageRequest.getCarId()),
				checkNumberOfCarImages(createCarImageRequest.getCarId()));
		if (result != null) {
			return result;
		}
		CarImage carImage = modelMapperService.forRequest().map(createCarImageRequest, CarImage.class); //
		carImage.setImageDate(LocalDate.now());

		carImage.setImagePath(generateImage(createCarImageRequest.getFile()).toString());
		this.carImageDao.save(carImage);
		return new SuccessResult("araba resmi eklendi");
	}

	@Override
	public Result update(UpdateCarImageRequest updateCarImageRequest) throws IOException {
		Result result = BusinessRules.run(checkByImage(updateCarImageRequest.getImageId()),
				existsByCar(updateCarImageRequest.getCarId()));
		if (result != null) {
			return result;
		}
		CarImage carImage = modelMapperService.forRequest().map(updateCarImageRequest, CarImage.class); //
		carImage.setImageDate(LocalDate.now());
		carImage.setImagePath(generateImage(updateCarImageRequest.getFile()).toString());
		this.carImageDao.save(carImage);
		return new SuccessResult("Araba güncellendi");

	}

	@Override
	public Result delete(DeleteCarImagesRequest deleteCarImagesRequest) {
		Result result = BusinessRules.run(checkByImage(deleteCarImagesRequest.getImageId()));
		
		if (result != null) {
			return result;
		}
		
		CarImage carImage = this.carImageDao.getById(deleteCarImagesRequest.getImageId());
		
		fileHelper.deleteImage(carImage.getImagePath());
		this.carImageDao.delete(carImage);
		
		return new SuccessResult("Araba image silindi");
	}

	private File generateImage(MultipartFile file) throws IOException {

		String imagePathGuid = java.util.UUID.randomUUID().toString(); // yeni bir guid oluşturduk. ve değişkene atadık.

		File imageFile = new File(FilePathConfiguration.mainPath + imagePathGuid + "."
				+ file.getContentType().substring(file.getContentType().indexOf("/") + 1));

		imageFile.createNewFile();
		FileOutputStream outputImage = new FileOutputStream(imageFile);
		outputImage.write(file.getBytes());
		outputImage.close();

		return imageFile;
	}

	private Result checkNumberOfCarImages(int carId) {
		if (this.carImageDao.countCarImageByCar_CarId(carId) >= 5) {
			return new ErrorResult("Araba mevcut halde 5 tane fotoğraf içeriyor");
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<List<CarImagesDto>> getCarImageByCarId(int carId) {
		Result resultcheck = BusinessRules.run(existsByCar(carId));

		if (resultcheck != null) {
			return new ErrorDataResult<List<CarImagesDto>>(null, "araba bulunamadı");
		}

		List<CarImage> carImages = this.checkIfCarImageExists(carId).getData();
		List<CarImagesDto> result = carImages.stream()
				.map(carImage -> modelMapperService.forDto().map(carImage, CarImagesDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult(result, "Araç Resimleri Listelendi");
	}

	private DataResult<List<CarImage>> checkIfCarImageExists(int carId) {

		if (this.carImageDao.existsByCar_CarId(carId)) {
			List<CarImage> result = this.carImageDao.getByCar_CarId(carId);
			return new SuccessDataResult<List<CarImage>>(result);
		}
		List<CarImage> carImages = new ArrayList<CarImage>();
		CarImage carImage = new CarImage();
		carImage.setImagePath(FilePathConfiguration.mainPath + FilePathConfiguration.defaultImage);
		carImages.add(carImage);
		return new SuccessDataResult<List<CarImage>>(carImages, "araç resimleri listelendi");
	}

	private Result checkByImage(int imageId) {
		if (!this.carImageDao.existsById(imageId)) {
			return new ErrorResult("image Id Bulunamadı");
		}
		return new SuccessResult();
	}

	private Result existsByCar(int carId) {

		if (!this.carService.checkCarExists(carId).isSuccess()) {
			return new ErrorResult("Araba Bulunamadı");
		}
		return new SuccessResult();
	}
}
