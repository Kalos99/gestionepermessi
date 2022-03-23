package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.repository.messaggio.MessaggioRepository;

@Service
public class MessaggioServiceImpl implements MessaggioService {
	
	@Autowired
	private MessaggioRepository repository;

	@Transactional(readOnly = true)
	public List<Messaggio> listAllMessaggi() {
		return (List<Messaggio>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Messaggio caricaSingoloMessaggio(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Messaggio messaggioInstance) {
		repository.save(messaggioInstance);		
	}

	@Transactional
	public void inserisciNuovo(Messaggio messaggioInstance) {
		repository.save(messaggioInstance);	
	}

	@Transactional
	public void rimuovi(Messaggio messaggioInstance) {
		repository.delete(messaggioInstance);
	}

	@Transactional(readOnly = true)
	public Page<Messaggio> findByExample(Messaggio example, Integer pageNo, Integer pageSize, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
