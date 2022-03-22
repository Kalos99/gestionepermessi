package it.prova.gestionepermessi.repository.richiestapermesso;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RichiestaPermessoRepository extends PagingAndSortingRepository<RichiestaPermessoRepository, Long>, JpaSpecificationExecutor<RichiestaPermessoRepository>{

}
