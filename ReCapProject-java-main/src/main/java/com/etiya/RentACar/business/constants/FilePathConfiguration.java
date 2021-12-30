package com.etiya.RentACar.business.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

@Data
public class FilePathConfiguration {

	//C:\Users\emin.sahan\Documents\rentalCarMsGithub\RentalACar\ReCapProject-java-main\image


	@Value("${main.path}")
	private   String mainPath;

	@Value("${default.name}")
	private   String defaultImage;



}
