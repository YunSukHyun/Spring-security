package net.dsa.todo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.todo.model.User;
import net.dsa.todo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {
	private final UserService userService;
	
	// Admin page main
	@GetMapping("admin")
	public String admin() {
		log.info("Admin main page");
		return "admin/main";
	}
	
	// Admin login page 
	@GetMapping("admin/login")
	public String login() {
		return "admin/login";
	}
	
	// Member list
	@GetMapping("admin/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAllUsers());
		return "admin/list";
	}
	
	// Member detail
	@GetMapping("admin/users/{id}")
	public String details(@PathVariable(name="id") String id, Model model) {
		model.addAttribute("user", userService.findUser(id));
		return "admin/edit";
	}
	
	// 매니저, 어드민 모두 접근 가능
	@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
	@PostMapping("admin/users/{id}/edit")
	public String edit(@PathVariable(name="id") String id, @ModelAttribute User user) {
		userService.updateUser(id, user);
		return "redirect:/admin/users/";
	}
		
	// 어드민만 접근 가능
//	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("admin/users/{id}/delete")
	public String delete(@PathVariable(name="id") String id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}
}
