package it.prova.gestionepermessi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.richiestapermesso.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService {
	
	@Autowired
	private RichiestaPermessoRepository repository;

	@Override
	public List<RichiestaPermesso> listAllRichieste() {
		return (List<RichiestaPermesso>) repository.findAll();
	}

	@Override
	public RichiestaPermesso caricaSingolaRichiesta(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(RichiestaPermesso richiestaInstance) {
		repository.save(richiestaInstance);
		
	}

	@Override
	public void inserisciNuovo(RichiestaPermesso richiestaInstance) {
		repository.save(richiestaInstance);
		
	}

	@Override
	public void rimuovi(RichiestaPermesso richiestaInstance) {
		repository.delete(richiestaInstance);
		
	}

	@Override
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}
}