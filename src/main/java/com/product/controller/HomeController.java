package com.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.product.entity.Products;
import com.product.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/")
	public String home(Model m)
	{
		
		List<Products> list = productRepo.findAll();
		m.addAttribute("all_products", list);
		
		return "index";
	}

	@GetMapping("/load_form")
	public String loadForm()
	{
		return "add";
	}

	@GetMapping("/edit_form/{id}")
	public String editForm(@PathVariable(value="id") long id,Model m)
	{
		
	  Optional<Products> product=	productRepo.findById(id); //to handle nullpointer exception
	  Products pro=product.get();
	  m.addAttribute("product", pro);
		
		return "edit";
	}
	
	@PostMapping("/save_products")
	public String saveProducts(@ModelAttribute Products products,HttpSession session) {
		
		productRepo.save(products);
		session.setAttribute("msg", "Product Added Successfully..");
		
		return "redirect:/load_form";
	}
	
	
	@PostMapping("/update_products")
	public String updateProducts(@ModelAttribute Products products,HttpSession session) {
		
		productRepo.save(products);
		session.setAttribute("msg", "Product Updated Successfully..");
		
		return "redirect:/";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProducts(@PathVariable(value="id") long id, HttpSession session)
	{
		productRepo.deleteById(id);
		session.setAttribute("msg", "Product Deleted Successfully..");
		
		return "redirect:/";
	}
	
	

}
