package com.etiya.RentACar.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACar.business.abstracts.UserService;
import com.etiya.RentACar.business.dtos.CarSearchListDto;
import com.etiya.RentACar.business.dtos.UserSearchListDto;
import com.etiya.RentACar.core.utilities.mapping.ModelMapperService;
import com.etiya.RentACar.core.utilities.results.DataResult;
import com.etiya.RentACar.core.utilities.results.ErrorResult;
import com.etiya.RentACar.core.utilities.results.Result;
import com.etiya.RentACar.core.utilities.results.SuccessDataResult;
import com.etiya.RentACar.core.utilities.results.SuccessResult;
import com.etiya.RentACar.dataAccess.abstracts.UserDao;
import com.etiya.RentACar.entites.Car;
import com.etiya.RentACar.entites.User;

@Service
public class UserManager implements UserService{

	private UserDao userDao;
	private ModelMapperService modelMapperService;
	@Autowired
	public UserManager(UserDao userDao,ModelMapperService modelMapperService) {
		super();
		this.userDao = userDao;
		this.modelMapperService = modelMapperService;
	}
	@Override
	public Result existsByEmail(String email) {
		if (this.userDao.existsByEmail(email)) {
			return new ErrorResult("User bulunumadı.");
		}
		return new SuccessResult();
	}
	@Override
	public DataResult<UserSearchListDto> getByEmail(String email) {

		User user = this.userDao.getByEmail(email);
		UserSearchListDto userSearchListDto = modelMapperService.forDto().map(user, UserSearchListDto.class);
		return new SuccessDataResult<UserSearchListDto>(userSearchListDto);
		
	}
	@Override
	public DataResult<UserSearchListDto> getById(int id) {
		User user = this.userDao.getById(id);
		UserSearchListDto userSearchListDto = modelMapperService.forDto().map(user, UserSearchListDto.class);
		return new SuccessDataResult<UserSearchListDto>(userSearchListDto);
	}
	@Override
	public Result existsById(int userId) {
		if(!this.userDao.existsById(userId)) {
			return new ErrorResult("user bulunamadı");
		}
		return new SuccessResult();
	}

}
