package co.edu.usco.pw.parcial_consultas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import co.edu.usco.pw.parcial_consultas.model.Medico;
import co.edu.usco.pw.parcial_consultas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Medicos", description = "Servicios web para gestionar los medicos de la clinica")
public class MedicoRestController {

	@Autowired
	private MedicoService medicoService;

	@Operation(summary = "Listar medicos", description = "Devuelve los medicos de la tabla medicos, son los que se cargan en la lista desplegable del formulario de consultas.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, lista de medicos"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no tiene un rol valido") })
	@GetMapping
	public List<Medico> listar() {
		return medicoService.listar();
	}

	@Operation(summary = "Buscar un medico", description = "Devuelve un medico por su ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, medico encontrado"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no tiene un rol valido"),
			@ApiResponse(responseCode = "404", description = "No existe un medico con ese ID") })
	@GetMapping("/{id}")
	public ResponseEntity<Medico> buscar(@Parameter(description = "ID del medico", example = "1") @PathVariable Long id) {
		Medico medico = medicoService.buscar(id);
		if (medico == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(medico);
	}

	@Operation(summary = "Crear un medico", description = "Registra un medico y le crea su cuenta con rol MEDICO para que pueda iniciar sesion. El usuario debe ser unico y la contrasena de minimo 4 caracteres. Solo ADMINISTRADOR.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, medico creado"),
			@ApiResponse(responseCode = "400", description = "Algun dato no cumple las validaciones o el usuario ya existe"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMINISTRADOR") })
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Medico medico,
			@Parameter(description = "Contrasena para la cuenta del medico", example = "1234") @RequestParam String password) {
		String error = medicoService.validar(medico, password, true);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		medico.setId(null);
		return ResponseEntity.ok(medicoService.guardar(medico, password));
	}

	@Operation(summary = "Editar un medico", description = "Cambia el nombre y la especialidad. El usuario no se cambia. Si se envia password se cambia la contrasena. Solo ADMINISTRADOR.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, medico editado"),
			@ApiResponse(responseCode = "400", description = "Algun dato no cumple las validaciones"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe un medico con ese ID") })
	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(
			@Parameter(description = "ID del medico a editar", example = "1") @PathVariable Long id,
			@RequestBody Medico medico,
			@Parameter(description = "Nueva contrasena (opcional)") @RequestParam(required = false) String password) {
		if (medicoService.buscar(id) == null) {
			return ResponseEntity.notFound().build();
		}
		String error = medicoService.validar(medico, password, false);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.ok(medicoService.actualizar(id, medico, password));
	}

	@Operation(summary = "Eliminar un medico", description = "Borra un medico y su cuenta. No se puede si tiene consultas asignadas. Solo ADMINISTRADOR.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK, medico eliminado"),
			@ApiResponse(responseCode = "400", description = "El medico tiene consultas asignadas"),
			@ApiResponse(responseCode = "401", description = "Unauthorized, no ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "Forbidden, el usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe un medico con ese ID") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(
			@Parameter(description = "ID del medico a eliminar", example = "3") @PathVariable Long id) {
		if (medicoService.buscar(id) == null) {
			return ResponseEntity.notFound().build();
		}
		String error = medicoService.eliminar(id);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.ok("Medico eliminado");
	}

}
