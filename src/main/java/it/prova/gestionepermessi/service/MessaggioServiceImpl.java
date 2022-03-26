package it.prova.gestionepermessi.service;

import java.util.ArrayList;
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
		Specification<Messaggio> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getOggetto()))
				predicates.add(cb.like(cb.upper(root.get("oggetto")), "%" + example.getOggetto().toUpperCase() + "%"));
			
			if (StringUtils.isNotEmpty(example.getTesto()))
				predicates.add(cb.like(cb.upper(root.get("testo")), "%" + example.getTesto().toUpperCase() + "%"));
			
			if (example.getLetto() != null)
				predicates.add(cb.equal(root.get("letto"), example.getLetto()));

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
	public Messaggio caricaSingoloMessaggioEager(Long id) {
		return repository.findSingleMessageEager(id);
	}

	@Override
	public void leggiMessaggio(Long idMessaggio) {
		Messaggio messaggioReloaded = repository.findById(idMessaggio).get();
		messaggioReloaded.setLetto(true);
		repository.save(messaggioReloaded);
	}

}
