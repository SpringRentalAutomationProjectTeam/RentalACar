package com.etiya.RentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACar.entites.User;

public interface UserDao extends JpaRepository<User, Integer>{

	User getByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsById(int userId);
}
