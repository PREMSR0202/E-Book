package com.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.LoginSecurityConfig;
import com.model.Books;
import com.model.Role;
import com.model.User;
import com.repository.RoleRepository;
import com.service.RoleService;
import com.service.UserService;
import com.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostConstruct
	public void init() {
		roleService.init();
		userService.init();
	}

	@GetMapping("/register")
	public String registration(Model model) {
		if (LoginSecurityConfig.isAuthenticated()) {
			return "Home";
		}
		model.addAttribute("userForm", new User());
		return "Register";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "Login";
	}

	@PostMapping("/register")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "Register";
		}
		userForm.setPwd(bCryptPasswordEncoder.encode(userForm.getPwd()));
		Role userRole = roleRepository.findByName("USER");
		userForm.setRole(userRole);
		userForm.setAccount(false);

		userService.save(userForm);

		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLogin(Model model) {
		if (LoginSecurityConfig.isAuthenticated()) {
			return "redirect:/listbooks";
		}
		return "Login";
	}

	@GetMapping("/")
	public String home(Books books, HttpServletRequest request) {
		return "newindex";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/manageuser", method = RequestMethod.GET)
	public String listUsers(Model model, Books books, HttpSession session) {
		model.addAttribute("download", session.getAttribute("download"));
		model.addAttribute("roll", session.getAttribute("role"));
		model.addAttribute("account", session.getAttribute("account"));
		model.addAttribute("flag", session.getAttribute("flag"));
		model.addAttribute("listofusers", userService.findAll());
		return "manageuser";
	}

	@GetMapping("/showinvoice")
	public String showInvoice(@RequestParam(value = "path") String path, Model model) {
		model.addAttribute("path", path + "#toolbar=0&");
		return "showinvoice";
	}

	@RequestMapping(value = "/changeroles", method = RequestMethod.GET)
	public String changeRoles(@RequestParam(value = "id") int id) {

		User u = userService.findbyID(id);
		if (u.getRole().getId() == 1) {

			Role changerole = roleRepository.findByName("USER");
			u.setRole(changerole);
			userService.save(u);

		} else if (u.getRole().getId() == 2) {
			Role changerole = roleRepository.findByName("ADMIN");
			u.setRole(changerole);
			userService.save(u);
		}
		return "redirect:/manageuser";
	}

}
