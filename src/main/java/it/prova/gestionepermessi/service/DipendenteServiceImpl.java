package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService{
	
	@Autowired
	private DipendenteRepository repository;

	@Transactional(readOnly = true)
	public List<Dipendente> listAllDipendenti() {
		return (List<Dipendente>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Dipendente caricaSingoloDipendente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Dipendente caricaSingoloDipendenteConRichiestePermesso(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void aggiorna(Dipendente dipendenteInstance) {		
		repository.save(dipendenteInstance);
	}

	@Transactional
	public void inserisciNuovo(Dipendente dipendenteInstance) {
		repository.save(dipendenteInstance);	
	}

	@Transactional
	public void rimuovi(Dipendente dipendenteInstance) {
		repository.delete(dipendenteInstance);	
	}
	
	@Transactional(readOnly = true)
	public Page<Dipendente> findByExample(Dipendente example, Integer pageNo, Integer pageSize, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
