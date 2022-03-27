package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.RichiestaPermesso;

public interface RichiestaPermessoService {
	
	public List<RichiestaPermesso> listAllRichieste() ;

	public RichiestaPermesso caricaSingolaRichiesta(Long id);
	
	public RichiestaPermesso caricaSingolaRichiestaEager(Long id);

	public void aggiorna(RichiestaPermesso richiestaInstance);

	public void inserisciNuovo(RichiestaPermesso richiestaInstance);
	
	 public void inserisciNuovaECreaMessaggio(RichiestaPermesso richiestaInstance);

	public void rimuovi(Long id);

	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize, String sortBy);
	
	public void changeState(Long richiestaInstanceId);
	
	public List<RichiestaPermesso> listAllElementsByUsername(String usernameInput);
	
	public void aggiornaRichiestaECreaNuovoMessaggio(RichiestaPermesso richiestaInstance);

}
