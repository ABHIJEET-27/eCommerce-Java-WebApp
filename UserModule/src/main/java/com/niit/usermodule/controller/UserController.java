package com.niit.usermodule.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.usermodule.dao.UserDAO;
import com.niit.usermodule.model.User;


@Controller
public class UserController {

	@Autowired HttpSession session;
	@Autowired User user;
	@Autowired UserDAO userDAO;
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping("/Login")
	public ModelAndView goToLogin(){
		log.debug("Starting of Login Method");
		ModelAndView mv = new ModelAndView("Home");
		mv.addObject("isUserClickedLogin", "true");
		log.debug("Ending of Login Method");
		return mv;
	}
	
	@RequestMapping("/Register")
	public ModelAndView registerUser(){
		log.debug("Starting of registerUser Method");
		ModelAndView mv = new ModelAndView("Home");
		mv.addObject("isUserClickedRegister", "true");
		
		log.debug("Ending of registerUser Method");
		return mv;
	}
	
	@RequestMapping("/JoinUs")
	public ModelAndView joinUs(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("contact") String contact){
		log.debug("Starting of method joinUs");
		ModelAndView mv = new ModelAndView("redirect:/Login");
		log.info("User about to register");
		
		user.setId(id);
		user.setPassword(password);
		user.setName(name);
		user.setContact(contact);
		user.setRole("ROLE_USER");
		if (userDAO.save(user)) {
			log.info("Registration Successful");
			session.setAttribute("successMessage","Registration Successful. Please login to continue. ");
		} else {
			log.info("Registration falied");
			session.setAttribute("errorMessage","Failed to Register. ");
		}
		
		
		log.debug("Ending of method manageUserAdd");
		return mv;
	}
	
	@RequestMapping("/ViewUsers")
	public ModelAndView viewUsers(){
		log.debug("Starting of viewUsers Method");
		ModelAndView mv = new ModelAndView("Home");
		mv.addObject("isUserClickedViewUsers", "true");
		log.debug("Ending of Login Method");
		return mv;
	}
	
	
	
	@RequestMapping("/manage-user-delete/{id}")
	public ModelAndView manageUserDelete(@PathVariable("id") String id){
		log.debug("Starting of method manageUserDelete");
		ModelAndView mv = new ModelAndView("redirect:/manageUsers");
		log.info("You are about to delete user");
		String loggedInUser = (String) session.getAttribute("loggedInUser"); 
		log.debug("Logged in user id"+loggedInUser);
		log.debug("Delete user id"+id);
		if(loggedInUser==id){
			session.setAttribute("errorMessage", "Cannot delete loggedIn User Id. ");
		}
		else{
			if(userDAO.delete(id)){
				mv.addObject("successMessage", "Successfully deleted user with id: "+id+". ");
			} else {
				mv.addObject("errorMessage", "Falied to delete user with id: "+id+". ");
			}
		}
		
		
		
		log.debug("Ending of method manageUserDelete");
		return mv;
	}
	
	@RequestMapping("/manage-user-edit/{id}")
	public ModelAndView manageUserEdit(@PathVariable("id") String id){
		log.debug("Starting of method manageUserEdit");
		ModelAndView mv = new ModelAndView("redirect:/manageUsers");
		log.info("You are about to delete user");
		user=userDAO.get(id);
		
		session.setAttribute("selectedUser", user);
		session.setAttribute("isAdminClickedEditUser", "true");
		
		log.debug("Ending of method manageUserEdit");
		return mv;
	}
	@RequestMapping("/manage-user-update")
	public ModelAndView manageUserUpdate(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("contact") String contact, @RequestParam("role") String role){
		log.debug("Starting of method manageUserUpdate");
		ModelAndView mv = new ModelAndView("redirect:/manageUsers");
		log.info("You are about to update user");
		
		user.setId(id);
		user.setPassword(password);
		user.setName(name);
		user.setContact(contact);
		user.setRole(role);
		if (userDAO.update(user)) {
			log.info("Update user Successful");
			session.setAttribute("successMessage","Update  user Successful. ");
			session.setAttribute("isAdminClickedEditUser", "false");
		} else {
			log.info("Update user falied");
			session.setAttribute("errorMessage","Update  user failed. ");
		}
		
		
		log.debug("Ending of method manageUserUpdate");
		return mv;
	}
	@RequestMapping("/manage-user-add")
	public ModelAndView manageUserAdd(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("contact") String contact, @RequestParam("role") String role){
		log.debug("Starting of method manageUserAdd");
		ModelAndView mv = new ModelAndView("redirect:/manageUsers");
		log.info("You are about to add user");
		
		user.setId(id);
		user.setPassword(password);
		user.setName(name);
		user.setContact(contact);
		user.setRole(role);
		if (userDAO.save(user)) {
			log.info("Add user Successful");
			session.setAttribute("successMessage","Add  user Successful. ");
		} else {
			log.info("Add user falied");
			session.setAttribute("errorMessage","Add  user failed. ");
		}
		
		
		log.debug("Ending of method manageUserAdd");
		return mv;
	}
}
