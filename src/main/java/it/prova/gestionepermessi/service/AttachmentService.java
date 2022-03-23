package it.prova.gestionepermessi.service;

import java.util.List;

import it.prova.gestionepermessi.model.Attachment;

public interface AttachmentService {
	
	public List<Attachment> listAllElements() ;

	public Attachment caricaSingolo(Long id);

	public void aggiorna(Attachment attachmentInstance);

	public void inserisciNuovo(Attachment attachmentInstance);

	public void rimuovi(Long id);
}