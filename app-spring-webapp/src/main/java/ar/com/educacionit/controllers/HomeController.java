package ar.com.educacionit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	//http://misitio.com.ar/ba
	
	@RequestMapping(value = {"/","home"})
	public String home() {
		System.out.println("homeController");
		return "index";//templates/index.html
	}
}
