package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;

public interface DipendenteService {
	
	public List<Dipendente> listAllDipendenti() ;

	public Dipendente caricaSingoloDipendente(Long id);
	
	public Dipendente caricaSingoloDipendenteConRichiestePermesso(Long id);

	public void aggiorna(Dipendente utenteInstance);

	public void inserisciNuovo(Dipendente utenteInstance);

	public void rimuovi(Dipendente utenteInstance);

	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy);
	
	public void inserisciNuovoECensisciUtente(Dipendente dipendenteInstance, Ruolo ruoloInput);
}
