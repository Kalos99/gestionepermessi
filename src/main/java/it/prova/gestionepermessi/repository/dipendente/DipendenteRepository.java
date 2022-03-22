package it.prova.gestionepermessi.repository.dipendente;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Dipendente;

public interface DipendenteRepository extends PagingAndSortingRepository<Dipendente, Long>, JpaSpecificationExecutor<Dipendente>{

}
