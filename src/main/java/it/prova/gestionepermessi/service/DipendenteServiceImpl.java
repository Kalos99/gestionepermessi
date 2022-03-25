package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.dipendente.DipendenteRepository;
import it.prova.gestionepermessi.repository.utente.UtenteRepository;

@Service
public class DipendenteServiceImpl implements DipendenteService{
	
	@Autowired
	private DipendenteRepository repository;
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Value("${defaultPassword}")
	private String defaultPassword;

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
		Specification<Dipendente> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if (StringUtils.isNotEmpty(example.getNome()))
				predicates.add(cb.like(cb.upper(root.get("nome")), "%" + example.getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates.add(cb.like(cb.upper(root.get("cognome")), "%" + example.getCognome().toUpperCase() + "%"));
			
			if (StringUtils.isNotEmpty(example.getCodFis()))
				predicates.add(cb.like(cb.upper(root.get("codFis")), "%" + example.getCodFis().toUpperCase() + "%"));
			
			if (StringUtils.isNotEmpty(example.getEmail()))
				predicates.add(cb.like(cb.upper(root.get("email")), "%" + example.getEmail().toUpperCase() + "%"));

			if (example.getDataNascita() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataNascita"), example.getDataNascita()));
			
			if (example.getDataAssunzione() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataAssunzione"), example.getDataAssunzione()));
			
			if (example.getDataDimissioni() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataDimissioni"), example.getDataDimissioni()));
			
			if (example.getSesso() != null)
				predicates.add(cb.equal(root.get("sesso"), example.getSesso()));

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
	
	@Transactional
	public void inserisciNuovoECensisciUtente(Dipendente dipendenteInstance, Ruolo ruoloInput) {
		Utente utenteInstance = new Utente();
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setUsername(Character.toLowerCase(dipendenteInstance.getNome().charAt(0)) + "." + dipendenteInstance.getCognome().toLowerCase());
		utenteInstance.setPassword(passwordEncoder.encode(defaultPassword)); 
		utenteInstance.setDateCreated(new Date());
		utenteInstance.setDipendente(dipendenteInstance);
		utenteInstance.getRuoli().add(ruoloInput);
		dipendenteInstance.setUtente(utenteInstance);
		utenteRepository.save(utenteInstance);
		
		dipendenteInstance.setEmail(Character.toLowerCase(dipendenteInstance.getNome().charAt(0)) + "." + dipendenteInstance.getCognome().toLowerCase() + "@prova.it");
		repository.save(dipendenteInstance);
	}
	
	@Transactional
	public void aggiornaDipendenteEUtente(Dipendente dipendenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Dipendente dipendenteReloaded = repository.findById(dipendenteInstance.getId()).orElse(null);
		Utente utenteAssociatoADipendente = dipendenteReloaded.getUtente();
		if (dipendenteReloaded == null || utenteAssociatoADipendente == null)
			throw new RuntimeException("Elemento non trovato");
		dipendenteInstance.setEmail(Character.toLowerCase(dipendenteInstance.getNome().charAt(0)) + "." + dipendenteInstance.getCognome().toLowerCase() + "@prova.it");
		dipendenteInstance.setUtente(utenteAssociatoADipendente);
		utenteAssociatoADipendente.setDipendente(dipendenteInstance);
		utenteAssociatoADipendente.setUsername(Character.toLowerCase(dipendenteInstance.getNome().charAt(0)) + "." + dipendenteInstance.getCognome().toLowerCase());
		repository.save(dipendenteInstance);
		utenteRepository.save(utenteAssociatoADipendente);
	}

	@Override
	public List<Dipendente> cercaByCognomeENomeILike(String term) {
		return repository.findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(term, term);
	}
}