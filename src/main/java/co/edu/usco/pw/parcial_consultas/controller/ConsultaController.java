package co.edu.usco.pw.parcial_consultas.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.usco.pw.parcial_consultas.model.Consulta;
import co.edu.usco.pw.parcial_consultas.service.ConsultaService;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

	@Autowired
	private ConsultaService consultaService;

	@GetMapping
	public String listar(Model model, Principal principal) {
		model.addAttribute("consultas", consultaService.listarSegunUsuario(principal.getName()));
		return "lista-consultas";
	}

	@GetMapping("/nueva")
	public String nueva(Model model) {
		model.addAttribute("consulta", new Consulta());
		model.addAttribute("medicos", consultaService.listarMedicos());
		model.addAttribute("pacientes", consultaService.listarPacientes());
		return "nueva-consulta";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Consulta consulta, Model model) {
		String error = consultaService.validar(consulta);
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("consulta", consulta);
			model.addAttribute("medicos", consultaService.listarMedicos());
			model.addAttribute("pacientes", consultaService.listarPacientes());
			return "nueva-consulta";
		}
		consultaService.guardar(consulta);
		return "redirect:/consultas";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Consulta consulta = consultaService.buscar(id);
		if (consulta == null) {
			return "redirect:/consultas";
		}
		model.addAttribute("consulta", consulta);
		model.addAttribute("medicos", consultaService.listarMedicos());
		model.addAttribute("pacientes", consultaService.listarPacientes());
		return "editar-consulta";
	}

	@PostMapping("/actualizar/{id}")
	public String actualizar(@PathVariable Long id, @ModelAttribute Consulta consulta, Model model) {
		if (consultaService.buscar(id) == null) {
			return "redirect:/consultas";
		}
		String error = consultaService.validar(consulta);
		if (error != null) {
			consulta.setId(id);
			model.addAttribute("error", error);
			model.addAttribute("consulta", consulta);
			model.addAttribute("medicos", consultaService.listarMedicos());
			model.addAttribute("pacientes", consultaService.listarPacientes());
			return "editar-consulta";
		}
		consultaService.actualizar(id, consulta);
		return "redirect:/consultas";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		consultaService.eliminar(id);
		return "redirect:/consultas";
	}

	@GetMapping("/horario/{id}")
	public String editarHorario(@PathVariable Long id, Model model, Principal principal) {
		Consulta consulta = consultaService.buscar(id);
		if (consulta == null || !consultaService.esSuCita(consulta, principal.getName())) {
			return "redirect:/403";
		}
		model.addAttribute("consulta", consulta);
		return "editar-horario";
	}

	@PostMapping("/horario/{id}")
	public String actualizarHorario(@PathVariable Long id, @RequestParam String horaInicio,
			@RequestParam String horaFin, Model model, Principal principal) {
		Consulta consulta = consultaService.buscar(id);
		if (consulta == null || !consultaService.esSuCita(consulta, principal.getName())) {
			return "redirect:/403";
		}
		String error = consultaService.validarHorario(horaInicio, horaFin);
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("consulta", consulta);
			return "editar-horario";
		}
		consultaService.actualizarHorario(id, horaInicio, horaFin);
		return "redirect:/consultas";
	}

}
