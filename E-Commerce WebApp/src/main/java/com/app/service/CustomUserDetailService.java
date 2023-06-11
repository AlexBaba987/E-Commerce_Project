package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.model.CustomUserDetails;
import com.app.model.User;
import com.app.repository.IUserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user=userRepository.findByEmail(email);
		user.orElseThrow(()->new UsernameNotFoundException("User not found"));
		return user.map(CustomUserDetails::new).get();
	}

}
