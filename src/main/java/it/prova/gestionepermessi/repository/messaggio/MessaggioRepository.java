package it.prova.gestionepermessi.repository.messaggio;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends PagingAndSortingRepository<Messaggio, Long>, JpaSpecificationExecutor<Messaggio>{
	
	@Query("from Messaggio m join fetch m.richiesta r where m.id = ?1")
	Messaggio findSingleMessageEager(Long id);
	
	@Query("select count(*) from Messaggio m where m.letto = ?1")
	Integer countByLetto(boolean letto);

}
