package com.etiya.RentACar.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACar.business.abstracts.ColorService;
import com.etiya.RentACar.business.requests.color.CreateColorRequest;
import com.etiya.RentACar.business.requests.color.DeleteColorRequest;
import com.etiya.RentACar.business.requests.color.UpdateColorRequest;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.entites.Color;

@RestController
@RequestMapping("api/colors/")
public class ColorsController {

	private ColorService colorService;

	@Autowired
	private ColorsController(ColorService colorService) {
		super();
		this.colorService = colorService;
	}

	@GetMapping("getAll")
	public DataResult<List<Color>> getAll() {
		return this.colorService.getAll();
	}

	@PostMapping("add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) {
		return this.colorService.add(createColorRequest);

	}

	@PutMapping("update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) {
		return this.colorService.update(updateColorRequest);
	}

	@DeleteMapping("delete")
	public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) {
		return this.colorService.delete(deleteColorRequest);
	}

}
