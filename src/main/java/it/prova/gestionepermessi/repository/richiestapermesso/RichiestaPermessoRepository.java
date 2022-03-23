package it.prova.gestionepermessi.repository.richiestapermesso;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoRepository extends PagingAndSortingRepository<RichiestaPermesso, Long>, JpaSpecificationExecutor<RichiestaPermesso>{

}
