package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpringDevtools {
	
	@GetMapping("/me")
	public String introduceme(Model model) {
		model.addAttribute("name","이태훈");
		model.addAttribute("job","무직");
		model.addAttribute("age",19);
		return "introduceme";
	}

}
