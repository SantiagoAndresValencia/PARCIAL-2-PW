package co.edu.usco.pw.parcial_consultas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.parcial_consultas.model.Medico;
import co.edu.usco.pw.parcial_consultas.model.Usuario;
import co.edu.usco.pw.parcial_consultas.repository.ConsultaRepository;
import co.edu.usco.pw.parcial_consultas.repository.MedicoRepository;
import co.edu.usco.pw.parcial_consultas.repository.UsuarioRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<Medico> listar() {
		return medicoRepository.findAll();
	}

	public Medico buscar(Long id) {
		return medicoRepository.findById(id).orElse(null);
	}

	public Medico guardar(Medico medico, String password) {
		Usuario usuario = new Usuario();
		usuario.setUsername(medico.getUsuario());
		usuario.setPassword(passwordEncoder.encode(password));
		usuario.setRol("MEDICO");
		usuario.setNombre(medico.getNombre());
		usuarioRepository.save(usuario);
		return medicoRepository.save(medico);
	}

	public Medico actualizar(Long id, Medico datos, String password) {
		Medico medico = medicoRepository.findById(id).orElseThrow();
		medico.setNombre(datos.getNombre());
		medico.setEspecialidad(datos.getEspecialidad());
		Usuario usuario = usuarioRepository.findByUsername(medico.getUsuario());
		if (usuario != null) {
			usuario.setNombre(datos.getNombre());
			if (password != null && !password.isEmpty()) {
				usuario.setPassword(passwordEncoder.encode(password));
			}
			usuarioRepository.save(usuario);
		}
		return medicoRepository.save(medico);
	}

	public String eliminar(Long id) {
		Medico medico = medicoRepository.findById(id).orElse(null);
		if (medico == null) {
			return "El medico no existe";
		}
		if (consultaRepository.existsByMedicoId(id)) {
			return "No se puede eliminar a " + medico.getNombre() + " porque tiene consultas asignadas";
		}
		Usuario usuario = usuarioRepository.findByUsername(medico.getUsuario());
		if (usuario != null) {
			usuarioRepository.delete(usuario);
		}
		medicoRepository.delete(medico);
		return null;
	}

	public String validar(Medico medico, String password, boolean esNuevo) {
		if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
			return "El nombre del medico es obligatorio";
		}
		if (medico.getNombre().length() > 50) {
			return "El nombre no puede tener mas de 50 caracteres";
		}
		if (medico.getEspecialidad() != null && medico.getEspecialidad().length() > 50) {
			return "La especialidad no puede tener mas de 50 caracteres";
		}
		if (esNuevo) {
			String usuario = medico.getUsuario();
			if (usuario == null || usuario.isEmpty()) {
				return "El usuario es obligatorio";
			}
			if (usuario.length() > 20) {
				return "El usuario no puede tener mas de 20 caracteres";
			}
			for (int i = 0; i < usuario.length(); i++) {
				if (!Character.isLetterOrDigit(usuario.charAt(i))) {
					return "El usuario solo puede tener letras y numeros, sin espacios";
				}
			}
			if (usuarioRepository.findByUsername(usuario) != null) {
				return "Ya existe una cuenta con el usuario " + usuario;
			}
			if (password == null || password.length() < 4) {
				return "La contrasena debe tener minimo 4 caracteres";
			}
		} else {
			if (password != null && !password.isEmpty() && password.length() < 4) {
				return "La contrasena debe tener minimo 4 caracteres";
			}
		}
		return null;
	}

}
