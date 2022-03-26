package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Messaggio;

public interface MessaggioService {
	
	public List<Messaggio> listAllMessaggi() ;

	public Messaggio caricaSingoloMessaggio(Long id);
	
	public Messaggio caricaSingoloMessaggioEager(Long id);

	public void aggiorna(Messaggio messaggioInstance);

	public void inserisciNuovo(Messaggio messaggioInstance);

	public void rimuovi(Messaggio messaggioInstance);

	public Page<Messaggio> findByExample(Messaggio example, Integer pageNo, Integer pageSize, String sortBy);
	
	public void leggiMessaggio(Long idMessaggio);
}
