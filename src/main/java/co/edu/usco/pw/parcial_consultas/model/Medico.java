package co.edu.usco.pw.parcial_consultas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medicos")
@Schema(description = "Medico de la clinica")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID del medico", example = "1")
	private Long id;

	@Column(length = 50, nullable = false)
	@Schema(description = "Nombre del medico", example = "Ana Martinez")
	private String nombre;

	@Column(length = 50)
	@Schema(description = "Especialidad del medico", example = "Medicina general")
	private String especialidad;

	@Column(length = 20, unique = true, nullable = false)
	@Schema(description = "Usuario con el que el medico inicia sesion", example = "medico1")
	private String usuario;

}
