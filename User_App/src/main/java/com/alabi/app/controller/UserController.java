package com.alabi.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alabi.app.entity.User;
import com.alabi.app.service.RoleService;
import com.alabi.app.service.UserService;

@Controller
public class UserController {

	private UserService userService;
	private RoleService roleService;
	
	@Autowired
	public UserController(UserService userService, RoleService roleService) {
		super();
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping({"/"})
	public String homePage() {		
		return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping({"/list-user", "/list"})
	public ModelAndView listUser() {		
		ModelAndView mav = new ModelAndView("list-users");
		List <User> user = userService.read(); 
		mav.addObject("user", user);		
		return mav;
	}
	
	@GetMapping("/addnewuser")
	public ModelAndView addUserForm() {		
		ModelAndView mav = new ModelAndView("register-user-form");
		User user = new User();		
		mav.addObject("user", user);
		mav.addObject("roleList", roleService.readRole());
		return mav;
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {		
		
		
		try {
			if (user.getId() != null) {
				userService.edit(user);
			}else {
				userService.create(user);
				String successMessage = "a";
				redirectAttributes.addFlashAttribute("successMessage", successMessage);
			}
		}catch(DataIntegrityViolationException e) {
			
		}
		
		return "redirect:/addnewuser";
	}
	
	@GetMapping("/showUpdateForm")
	public ModelAndView showUpdateForm(@RequestParam Long userId) {		
		ModelAndView mav = new ModelAndView("register-user-form");
		mav.addObject("roleList", roleService.readRole());
		User user = userService.findById(userId);
		mav.addObject("user", user);
		return mav;
	}
	
	@GetMapping("/deleteUser")
	public String deleteEmployee(@RequestParam Long userId) {		
		userService.deleteById(userId);
		return "redirect:/list";
	}
}