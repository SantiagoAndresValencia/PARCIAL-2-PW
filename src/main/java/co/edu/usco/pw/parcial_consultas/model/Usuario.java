package co.edu.usco.pw.parcial_consultas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 20, unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(length = 20)
	private String rol;

	@Column(length = 50)
	private String nombre;

}
