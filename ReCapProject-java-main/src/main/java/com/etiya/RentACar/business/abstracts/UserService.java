package com.etiya.RentACar.business.abstracts;

import com.etiya.RentACar.business.dtos.UserSearchListDto;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.Result;

public interface UserService {
	
	DataResult<UserSearchListDto> getByEmail(String email);
	Result existsByEmail(String email);
	DataResult<UserSearchListDto> getById(int id); 
	
	Result existsById(int userId);
}
