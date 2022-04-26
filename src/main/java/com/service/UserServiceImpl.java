package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findbyID(int id) {
		return userRepository.findById(id).orElse(new User());
	}

	@Override
	public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		} else {
			throw new UsernameNotFoundException("Could not find any customer with the email " + email);
		}
	}

	@Override
	public User findByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPwd(encodedPassword);

		user.setResetPasswordToken(null);
		userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void init() {
		Role r = roleRepository.findByName("ADMIN");
		User admin = new User();
		admin.setId(1);
		admin.setFname("admin");
		admin.setLname("admin");
		admin.setEmail("admin@gmail.com");
		admin.setPwd(bCryptPasswordEncoder.encode("12345678"));
		admin.setAccount(true);
		admin.setRole(r);
		admin.setResetPasswordToken(null);
		userRepository.save(admin);
	}

}