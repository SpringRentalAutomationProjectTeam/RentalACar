package com.etiya.RentACar.ws;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACar.business.abstracts.AuthService;
import com.etiya.RentACar.business.requests.LoginRequest;
import com.etiya.RentACar.business.requests.IndıvidualCustomer.RegisterIndividualCustomerRequest;
import com.etiya.RentACar.core.utilities.results.Result;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	private AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/individualCustomerRegister")
	Result individualCustomerRegister(
			@RequestBody @Valid RegisterIndividualCustomerRequest registerIndividualCustomerRequest) {
		return this.authService.individualCustomerRegister(registerIndividualCustomerRequest);
	}

	@PostMapping("/login")
	Result login(@Valid @RequestBody LoginRequest loginRequest) {
		return this.authService.login(loginRequest);
	}
}
