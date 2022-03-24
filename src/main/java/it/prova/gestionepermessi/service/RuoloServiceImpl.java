package it.prova.gestionepermessi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.repository.ruolo.RuoloRepository;

@Service
public class RuoloServiceImpl implements RuoloService {
	
	@Autowired
	private RuoloRepository ruoloRepository;

	@Transactional(readOnly = true)
	public List<Ruolo> listAll() {
		return (List<Ruolo>)ruoloRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Ruolo caricaSingoloElemento(Long id) {
		return ruoloRepository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Ruolo ruoloInstance) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	public void inserisciNuovo(Ruolo ruoloInstance) {
		ruoloRepository.save(ruoloInstance);
	}

	@Transactional
	public void rimuovi(Ruolo ruoloInstance) {
		// TODO Auto-generated method stub
		
	}

	@Transactional(readOnly = true)
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) {
		return ruoloRepository.findByDescrizioneAndCodice(descrizione, codice);
	}

	@Override
	public List<Ruolo> listAllExceptAdmin() {
		List<Ruolo> ruoliDaMostrare = new ArrayList<Ruolo>();
		ruoliDaMostrare.add(ruoloRepository.findByDescrizioneAndCodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
		ruoliDaMostrare.add(ruoloRepository.findByDescrizioneAndCodice("Back Office User", "ROLE_BO_USER"));
		return ruoliDaMostrare;
	}

	@Transactional(readOnly = true)
	public Ruolo cercaPerCodice(String codice) {
		Ruolo ruolo = ruoloRepository.findByCodice(codice);
		System.out.println(ruolo.toString());
		return ruolo;
	}
}