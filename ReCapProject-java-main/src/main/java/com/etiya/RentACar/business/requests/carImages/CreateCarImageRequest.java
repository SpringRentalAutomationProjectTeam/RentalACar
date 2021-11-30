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
public class CreateCarImageRequest {
	
	@JsonIgnore
	private int id;
	
	private int carId;
	
	@JsonIgnore
	private String imagePath;
	
	@JsonIgnore
	private LocalDate imageDate;
	
	@JsonIgnore
	private MultipartFile file;
}
