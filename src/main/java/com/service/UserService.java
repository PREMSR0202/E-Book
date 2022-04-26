package com.service;

import java.util.List;

import com.model.User;

public interface UserService {

	public void save(User user);

	public User findByEmail(String email);

	public User findbyID(int id);

	public User findByResetPasswordToken(String token);

	public void updateResetPasswordToken(String token, String email);

	public void updatePassword(User user, String newPassword);

	public List<User> findAll();
	
	public void init();

}
