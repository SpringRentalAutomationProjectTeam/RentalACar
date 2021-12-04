package com.etiya.RentACar.business.abstracts;

import java.util.List;

import com.etiya.RentACar.business.requests.color.CreateColorRequest;
import com.etiya.RentACar.business.requests.color.DeleteColorRequest;
import com.etiya.RentACar.business.requests.color.UpdateColorRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Color;

public interface ColorService {
	DataResult<List<Color>> getAll();
	Result add(CreateColorRequest createColorRequest);
	Result update(UpdateColorRequest updateColorRequest);
	Result delete(DeleteColorRequest deleteColorRequest);
	Result checkIfColorExists(int colorId);
}
