package co.edu.usco.pw.parcial_consultas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.parcial_consultas.model.Consulta;
import co.edu.usco.pw.parcial_consultas.model.Medico;
import co.edu.usco.pw.parcial_consultas.model.Usuario;
import co.edu.usco.pw.parcial_consultas.repository.ConsultaRepository;
import co.edu.usco.pw.parcial_consultas.repository.MedicoRepository;
import co.edu.usco.pw.parcial_consultas.repository.UsuarioRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Consulta> listar() {
		return consultaRepository.findAll();
	}

	public List<Consulta> listarSegunUsuario(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario.getRol().equals("MEDICO")) {
			return consultaRepository.findByMedicoUsuario(username);
		}
		if (usuario.getRol().equals("PACIENTE")) {
			return consultaRepository.findByUsuarioPaciente(username);
		}
		return consultaRepository.findAll();
	}

	public Consulta buscar(Long id) {
		return consultaRepository.findById(id).orElse(null);
	}

	public List<Medico> listarMedicos() {
		return medicoRepository.findAll();
	}

	public List<Usuario> listarPacientes() {
		return usuarioRepository.findByRol("PACIENTE");
	}

	public Consulta guardar(Consulta consulta) {
		Medico medico = medicoRepository.findById(consulta.getMedico().getId()).orElse(null);
		consulta.setMedico(medico);
		return consultaRepository.save(consulta);
	}

	public Consulta actualizar(Long id, Consulta datos) {
		Consulta consulta = consultaRepository.findById(id).orElseThrow();
		Medico medico = medicoRepository.findById(datos.getMedico().getId()).orElse(null);
		consulta.setNombrePaciente(datos.getNombrePaciente());
		consulta.setMotivo(datos.getMotivo());
		consulta.setConsultorio(datos.getConsultorio());
		consulta.setHoraInicio(datos.getHoraInicio());
		consulta.setHoraFin(datos.getHoraFin());
		consulta.setMedico(medico);
		consulta.setUsuarioPaciente(datos.getUsuarioPaciente());
		return consultaRepository.save(consulta);
	}

	public Consulta actualizarHorario(Long id, String horaInicio, String horaFin) {
		Consulta consulta = consultaRepository.findById(id).orElseThrow();
		consulta.setHoraInicio(horaInicio);
		consulta.setHoraFin(horaFin);
		return consultaRepository.save(consulta);
	}

	public void eliminar(Long id) {
		consultaRepository.deleteById(id);
	}

	public boolean puedeVer(Consulta consulta, String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario.getRol().equals("ADMINISTRADOR")) {
			return true;
		}
		if (usuario.getRol().equals("MEDICO")) {
			return consulta.getMedico() != null && username.equals(consulta.getMedico().getUsuario());
		}
		return esSuCita(consulta, username);
	}

	public boolean esSuCita(Consulta consulta, String username) {
		return username.equals(consulta.getUsuarioPaciente());
	}

	public String validar(Consulta consulta) {
		String nombre = consulta.getNombrePaciente();
		if (nombre == null || nombre.trim().isEmpty()) {
			return "El nombre del paciente es obligatorio";
		}
		if (nombre.length() > 40) {
			return "El nombre del paciente no puede tener mas de 40 caracteres";
		}
		for (int i = 0; i < nombre.length(); i++) {
			char letra = nombre.charAt(i);
			if (!Character.isLetterOrDigit(letra) && letra != ' ') {
				return "El nombre del paciente solo puede tener letras, numeros y espacios";
			}
		}
		if (consulta.getMotivo() == null || consulta.getMotivo().trim().isEmpty()) {
			return "El motivo de la consulta es obligatorio";
		}
		if (consulta.getMotivo().length() > 100) {
			return "El motivo no puede tener mas de 100 caracteres";
		}
		if (consulta.getConsultorio() == null || consulta.getConsultorio() <= 0) {
			return "El numero de consultorio debe ser mayor a 0";
		}
		String errorHorario = validarHorario(consulta.getHoraInicio(), consulta.getHoraFin());
		if (errorHorario != null) {
			return errorHorario;
		}
		if (consulta.getMedico() == null || consulta.getMedico().getId() == null) {
			return "Debe seleccionar un medico";
		}
		if (!medicoRepository.existsById(consulta.getMedico().getId())) {
			return "El medico seleccionado no existe";
		}
		if (consulta.getUsuarioPaciente() == null || consulta.getUsuarioPaciente().isEmpty()) {
			return "Debe seleccionar la cuenta del paciente";
		}
		Usuario paciente = usuarioRepository.findByUsername(consulta.getUsuarioPaciente());
		if (paciente == null || !paciente.getRol().equals("PACIENTE")) {
			return "La cuenta seleccionada no es de un paciente";
		}
		return null;
	}

	public String validarHorario(String horaInicio, String horaFin) {
		if (horaInicio == null || horaInicio.isEmpty() || horaFin == null || horaFin.isEmpty()) {
			return "Debe ingresar la hora de inicio y la hora de finalizacion";
		}
		if (horaInicio.length() != 5 || horaFin.length() != 5) {
			return "Las horas deben estar en formato HH:mm, por ejemplo 08:30";
		}
		if (horaInicio.compareTo(horaFin) >= 0) {
			return "La hora de inicio debe ser menor que la hora de finalizacion";
		}
		return null;
	}

}
