package com.niit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.domain.Category;
import com.niit.shoppingcart.domain.Product;

@Controller
public class HomeController {

	
	//@Autowired User
	
	@Autowired HttpSession session;
	
	@Autowired Category category;
	
	@Autowired CategoryDAO categoryDAO;
	
	@Autowired Product product;
	
	@Autowired ProductDAO productDAO;

	@RequestMapping({"/","/Home"})
	public ModelAndView goToHome(){
		ModelAndView mv = new ModelAndView("Home");
		mv.addObject("message", "Thank you for visiting Shopping Cart! <br>");
		//model.addAttribute("message", "Thank you for visiting Shopping Cart! <br>");
		
		//get all categories
		List<Category> categoryList = categoryDAO.list();
		
		//attach to session
		session.setAttribute("categoryList", categoryList);
		session.setAttribute("category", category);
		
		
		//get all products
		List<Product> productList = productDAO.list();
		
		//attach to session
		session.setAttribute("productList", productList);
		session.setAttribute("product", product);
		
		
		return mv;
		//return "Home";
	}

	@RequestMapping("/LoginPage")
	public String loginPage(Model model) {
		model.addAttribute("isUserClickedLogin", "true");
		return "Home";
	}
	@RequestMapping("/RegistrationPage")
	public String registerPage(Model model) {
		model.addAttribute("isUserClickedRegistration", "true");
		return "Home";
	}
	@RequestMapping("/MainPage")
	public String mainPage(Model model) {
		model.addAttribute("isUserClickedMain", "true");
		return "Home";
	}
}
