package co.edu.usco.pw.parcial_consultas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.usco.pw.parcial_consultas.model.Medico;
import co.edu.usco.pw.parcial_consultas.service.MedicoService;

@Controller
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoService medicoService;

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("medicos", medicoService.listar());
		return "lista-medicos";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("medico", new Medico());
		return "nuevo-medico";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Medico medico, @RequestParam(required = false) String password,
			Model model) {
		String error = medicoService.validar(medico, password, true);
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("medico", medico);
			return "nuevo-medico";
		}
		medicoService.guardar(medico, password);
		return "redirect:/medicos";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Medico medico = medicoService.buscar(id);
		if (medico == null) {
			return "redirect:/medicos";
		}
		model.addAttribute("medico", medico);
		return "editar-medico";
	}

	@PostMapping("/actualizar/{id}")
	public String actualizar(@PathVariable Long id, @ModelAttribute Medico medico,
			@RequestParam(required = false) String password, Model model) {
		Medico actual = medicoService.buscar(id);
		if (actual == null) {
			return "redirect:/medicos";
		}
		String error = medicoService.validar(medico, password, false);
		if (error != null) {
			medico.setId(id);
			medico.setUsuario(actual.getUsuario());
			model.addAttribute("error", error);
			model.addAttribute("medico", medico);
			return "editar-medico";
		}
		medicoService.actualizar(id, medico, password);
		return "redirect:/medicos";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model model) {
		String error = medicoService.eliminar(id);
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("medicos", medicoService.listar());
			return "lista-medicos";
		}
		return "redirect:/medicos";
	}

}
