package it.prova.gestionepermessi.repository.messaggio;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioRepository extends PagingAndSortingRepository<Messaggio, Long>, JpaSpecificationExecutor<Messaggio>{

}
