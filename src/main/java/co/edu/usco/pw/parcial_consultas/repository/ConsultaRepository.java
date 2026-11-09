package co.edu.usco.pw.parcial_consultas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usco.pw.parcial_consultas.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	List<Consulta> findByMedicoUsuario(String usuario);

	List<Consulta> findByUsuarioPaciente(String usuarioPaciente);

	boolean existsByMedicoId(Long id);

}
