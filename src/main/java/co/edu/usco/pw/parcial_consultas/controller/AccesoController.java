package co.edu.usco.pw.parcial_consultas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoController {

	@GetMapping("/")
	public String inicio() {
		return "redirect:/consultas";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/403")
	public String accesoDenegado() {
		return "403";
	}

}
