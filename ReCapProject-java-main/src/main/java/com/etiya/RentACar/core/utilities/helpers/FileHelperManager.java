package com.etiya.RentACar.core.utilities.helpers;

import java.io.File;

import org.springframework.stereotype.Service;

import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessResult;

@Service
public class FileHelperManager implements FileHelper {

	@Override
	public Result deleteImage(String imagePath) {
		if (!imagePath.isEmpty()) {
			File file = new File(imagePath);
			file.delete();
		}
		return new SuccessResult();
	}

}
