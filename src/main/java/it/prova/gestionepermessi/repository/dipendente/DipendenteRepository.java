package it.prova.gestionepermessi.repository.dipendente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteRepository extends PagingAndSortingRepository<Dipendente, Long>, JpaSpecificationExecutor<Dipendente>{
	
	List<Dipendente> findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(String cognome, String nome);
	
	@Query("from Dipendente d left join fetch d.utente u where u.id = ?1")
	Optional<Dipendente> findByIdConUtente(Long id);

}
