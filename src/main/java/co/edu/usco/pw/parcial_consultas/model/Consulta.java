package co.edu.usco.pw.parcial_consultas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "consultas")
@Schema(description = "Consulta medica asignada a un medico y a un paciente")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "ID de la consulta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	private Long id;

	@Column(length = 40, nullable = false)
	@Schema(description = "Nombre del paciente, alfanumerico de maximo 40 caracteres", example = "Laura Torres", maxLength = 40)
	private String nombrePaciente;

	@Column(length = 100)
	@Schema(description = "Motivo de la consulta, maximo 100 caracteres", example = "Dolor de cabeza frecuente", maxLength = 100)
	private String motivo;

	@Schema(description = "Numero del consultorio", example = "101")
	private Integer consultorio;

	@Schema(description = "Hora de inicio en formato HH:mm", example = "08:00")
	private String horaInicio;

	@Schema(description = "Hora de finalizacion en formato HH:mm", example = "08:30")
	private String horaFin;

	@ManyToOne
	@JoinColumn(name = "medico_id")
	@Schema(description = "Medico asignado, basta con enviar el id")
	private Medico medico;

	@Column(length = 20)
	@Schema(description = "Usuario con el que el paciente inicia sesion", example = "paciente1")
	private String usuarioPaciente;

}
