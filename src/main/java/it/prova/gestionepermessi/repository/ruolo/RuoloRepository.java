package it.prova.gestionepermessi.repository.ruolo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionepermessi.model.Ruolo;

public interface RuoloRepository  extends CrudRepository<Ruolo, Long>{
	Ruolo findByDescrizioneAndCodice(String descrizione, String codice);
	
	@Query("from Ruolo r where r.codice = ?1")
	Ruolo findByCodice(String codice);
}
