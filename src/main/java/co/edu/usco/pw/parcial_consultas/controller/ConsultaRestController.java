package co.edu.usco.pw.parcial_consultas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.pw.parcial_consultas.model.Consulta;
import co.edu.usco.pw.parcial_consultas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Servicios web para gestionar las consultas medicas")
public class ConsultaRestController {

	@Autowired
	private ConsultaService consultaService;

	@Operation(summary = "Listar consultas", description = "El ADMINISTRADOR ve todas las consultas, el MEDICO solo las asignadas a el y el PACIENTE solo sus citas.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, lista de consultas segun el rol"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no tiene un rol valido") })
	@GetMapping
	public List<Consulta> listar(Principal principal) {
		return consultaService.listarSegunUsuario(principal.getName());
	}

	@Operation(summary = "Buscar una consulta", description = "Devuelve una consulta por su ID. El MEDICO solo puede ver las suyas y el PACIENTE solo sus citas.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, consulta encontrada"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, la consulta es de otro medico o de otro paciente"),
			@ApiResponse(responseCode = "404", description = "No existe una consulta con ese ID") })
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@Parameter(description = "ID de la consulta", example = "1") @PathVariable Long id,
			Principal principal) {
		Consulta consulta = consultaService.buscar(id);
		if (consulta == null) {
			return ResponseEntity.notFound().build();
		}
		if (!consultaService.puedeVer(consulta, principal.getName())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puede ver consultas que no son suyas");
		}
		return ResponseEntity.ok(consulta);
	}

	@Operation(summary = "Crear una consulta", description = "Registra una consulta. Nombre del paciente maximo 40 caracteres, motivo maximo 100, consultorio numerico, horas en formato HH:mm, el medico se envia con su id y usuarioPaciente es la cuenta del paciente. Solo ADMINISTRADOR.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, consulta creada"),
			@ApiResponse(responseCode = "400", description = "Algun dato no cumple las validaciones"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMINISTRADOR") })
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Consulta consulta) {
		String error = consultaService.validar(consulta);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		consulta.setId(null);
		return ResponseEntity.ok(consultaService.guardar(consulta));
	}

	@Operation(summary = "Editar una consulta", description = "Cambia todos los datos de una consulta existente. Solo ADMINISTRADOR.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, consulta editada"),
			@ApiResponse(responseCode = "400", description = "Algun dato no cumple las validaciones"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe una consulta con ese ID") })
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(
			@Parameter(description = "ID de la consulta a editar", example = "1") @PathVariable Long id,
			@RequestBody Consulta consulta) {
		if (consultaService.buscar(id) == null) {
			return ResponseEntity.notFound().build();
		}
		String error = consultaService.validar(consulta);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.ok(consultaService.actualizar(id, consulta));
	}

	@Operation(summary = "Eliminar una consulta", description = "Borra una consulta por su ID. Solo ADMINISTRADOR.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, consulta eliminada"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe una consulta con ese ID") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(
			@Parameter(description = "ID de la consulta a eliminar", example = "3") @PathVariable Long id) {
		if (consultaService.buscar(id) == null) {
			return ResponseEntity.notFound().build();
		}
		consultaService.eliminar(id);
		return ResponseEntity.ok("Consulta eliminada");
	}

	@Operation(summary = "Cambiar el horario de una cita", description = "El PACIENTE cambia la hora de inicio y de finalizacion, pero solo de las citas que tiene asignadas.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, horario actualizado"),
			@ApiResponse(responseCode = "400", description = "Las horas no son validas"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, no es PACIENTE o la cita no es suya"),
			@ApiResponse(responseCode = "404", description = "No existe una consulta con ese ID") })
	@PutMapping("/{id}/horario")
	public ResponseEntity<?> actualizarHorario(
			@Parameter(description = "ID de la cita", example = "1") @PathVariable Long id,
			@Parameter(description = "Nueva hora de inicio (HH:mm)", example = "09:00") @RequestParam String horaInicio,
			@Parameter(description = "Nueva hora de finalizacion (HH:mm)", example = "09:30") @RequestParam String horaFin,
			Principal principal) {
		Consulta consulta = consultaService.buscar(id);
		if (consulta == null) {
			return ResponseEntity.notFound().build();
		}
		if (!consultaService.esSuCita(consulta, principal.getName())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo puede cambiar el horario de sus propias citas");
		}
		String error = consultaService.validarHorario(horaInicio, horaFin);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.ok(consultaService.actualizarHorario(id, horaInicio, horaFin));
	}

}
