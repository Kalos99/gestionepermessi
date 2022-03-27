package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Attachment;
import it.prova.gestionepermessi.repository.attachment.AttachmentRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService{
	
	@Autowired
	private AttachmentRepository repository;

	@Transactional(readOnly = true)
	public List<Attachment> listAllElements() {
		return (List<Attachment>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Attachment caricaSingolo(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Attachment attachmentInstance) {
		repository.save(attachmentInstance);
	}

	@Transactional
	public void inserisciNuovo(Attachment attachmentInstance) {
		repository.save(attachmentInstance);
	}

	@Transactional
	public void rimuovi(Long id) {
		repository.deleteById(id);
	}
}