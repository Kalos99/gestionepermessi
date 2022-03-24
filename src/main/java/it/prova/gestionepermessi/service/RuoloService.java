package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Ruolo;

public interface RuoloService {
	
	public List<Ruolo> listAll() ;
	
	public List<Ruolo> listAllExceptAdmin() ;
	
	public Ruolo caricaSingoloElemento(Long id) ;

	public void aggiorna(Ruolo ruoloInstance) ;

	public void inserisciNuovo(Ruolo ruoloInstance) ;

	public void rimuovi(Ruolo ruoloInstance) ;

	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) ;
	
	public Ruolo cercaPerCodice(String codice) ;

}
