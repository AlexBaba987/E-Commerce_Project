package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.global.GlobalData;
import com.app.model.Role;
import com.app.model.User;
import com.app.repository.IRoleRepository;
import com.app.repository.IUserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	
	@GetMapping("/login")
	public String getLogin() {
		GlobalData.cart.clear();
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(@ModelAttribute("user") User user,HttpServletRequest request) throws Exception {
		String password=user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles=new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}
}
