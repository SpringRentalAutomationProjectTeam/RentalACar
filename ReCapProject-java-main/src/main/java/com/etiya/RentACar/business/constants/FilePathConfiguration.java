package com.etiya.RentACar.business.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class FilePathConfiguration {

	@Autowired
	private Environment environment;

//fauda
	public static String mainPath = "C:\\Users\\erdi.tuna\\Documents\\GitHub\\RentalACar\\ReCapProject-java-main\\image\\";
	public static String defaultImage = "default.jpg" ;

}
