package it.prova.gestionepermessi.repository.richiestapermesso;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoRepository extends PagingAndSortingRepository<RichiestaPermesso, Long>, JpaSpecificationExecutor<RichiestaPermesso>{	
	@Query("from RichiestaPermesso rp left join fetch rp.attachment a left join fetch rp.dipendente d where rp.id = ?1")
	RichiestaPermesso findSingleRichiestaEager(Long id);
	
	@Query("from RichiestaPermesso r left join fetch r.attachment a left join fetch r.dipendente d left join fetch d.utente u where u.username = ?1")
	public List<RichiestaPermesso> findByUsername(String usernameInput);
}
