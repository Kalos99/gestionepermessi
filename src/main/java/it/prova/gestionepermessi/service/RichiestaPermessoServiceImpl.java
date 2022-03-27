package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Messaggio;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.repository.messaggio.MessaggioRepository;
import it.prova.gestionepermessi.repository.richiestapermesso.RichiestaPermessoRepository;

@Service
public class RichiestaPermessoServiceImpl implements RichiestaPermessoService {
	
	@Autowired
	private RichiestaPermessoRepository repository;
	
	@Autowired
	private MessaggioRepository messaggioRepository;

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
		richiestaInstance.setApprovato(false);
		repository.save(richiestaInstance);
		
	}

	@Override
	public void rimuovi(RichiestaPermesso richiestaInstance) {
		repository.delete(richiestaInstance);
		
	}

	@Override
	public Page<RichiestaPermesso> findByExample(RichiestaPermesso example, Integer pageNo, Integer pageSize,
			String sortBy) {
		Specification<RichiestaPermesso> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if (example.getTipoPermesso() != null)
				predicates.add(cb.equal(root.get("tipoPermesso"), example.getTipoPermesso()));
			
			if (example.getApprovato() != null)
				predicates.add(cb.equal(root.get("approvato"), example.getApprovato()));

			if (StringUtils.isNotEmpty(example.getCodiceCertificato()))
				predicates.add(cb.like(cb.upper(root.get("codiceCertificato")), "%" + example.getCodiceCertificato().toUpperCase() + "%"));
			
			if (example.getDataInizio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataInizio"), example.getDataInizio()));
			
			if (example.getDataFine() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataFine"), example.getDataFine()));
			
			if (example.getDipendente()!=null && example.getDipendente().getId() != null)
				predicates.add(cb.equal(cb.upper(root.get("dipendente")), example.getDipendente().getId()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		
		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return repository.findAll(specificationCriteria, paging);
	}

	@Transactional(readOnly = true)
	public RichiestaPermesso caricaSingolaRichiestaEager(Long id) {
		return repository.findSingleRichiestaEager(id);
	}

	@Override
	public void changeState(Long richiestaInstanceId) {
		RichiestaPermesso richiestaInstance = caricaSingolaRichiesta(richiestaInstanceId);
		if(richiestaInstance == null)
			throw new RuntimeException("Elemento non trovato.");
		if(richiestaInstance.getDataInizio().before(new Date())) {
			throw new RuntimeException("mpossibile modificare stato richiesta");
		}
		
		if(!(richiestaInstance.getApprovato()))
			richiestaInstance.setApprovato(true);
		else 
			richiestaInstance.setApprovato(false);
		repository.save(richiestaInstance);
	}

	@Override
	public void inserisciNuovaECreaMessaggio(RichiestaPermesso richiestaInstance) {
		richiestaInstance.setApprovato(false);
		Messaggio nuovoMessaggio = new Messaggio();
		nuovoMessaggio.setLetto(false);
		nuovoMessaggio.setOggetto("Richiesta permesso da parte di " + richiestaInstance.getDipendente().getNome() + " " + richiestaInstance.getDipendente().getCognome());
		nuovoMessaggio.setTesto("Il dipendente " + richiestaInstance.getDipendente().getNome() + " " + richiestaInstance.getDipendente().getCognome() + " ha richiesto un permesso per " + richiestaInstance.getTipoPermesso() + " dal " + richiestaInstance.getDataInizio() + " al " + richiestaInstance.getDataFine());
        nuovoMessaggio.setRichiesta(richiestaInstance);
        
		repository.save(richiestaInstance);
		messaggioRepository.save(nuovoMessaggio);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<RichiestaPermesso> listAllElementsByUsername(String usernameInput) {
	  return (List<RichiestaPermesso>) repository.findByUsername(usernameInput);
	 }
}