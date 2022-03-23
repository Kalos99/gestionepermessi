package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {
	
	public List<RichiestaPermesso> listAllRichieste() ;

	public RichiestaPermesso caricaSingolaRichiesta(Long id);

	public void aggiorna(RichiestaPermesso richiestaInstance);

	public void inserisciNuovo(RichiestaPermesso richiestaInstance);

	public void rimuovi(RichiestaPermesso richiestaInstance);

	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize, String sortBy);

}
