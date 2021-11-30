package com.etiya.RentACar.business.requests.carImages;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarImageRequest {
	
	private int imageId;
	

	private int carId;
	
	private String imagePath;

	private LocalDate imageDate;
	
	@JsonIgnore
	private MultipartFile file;
}
