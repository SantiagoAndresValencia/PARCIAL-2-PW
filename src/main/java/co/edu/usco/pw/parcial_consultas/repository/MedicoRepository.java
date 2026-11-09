package co.edu.usco.pw.parcial_consultas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usco.pw.parcial_consultas.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

}
