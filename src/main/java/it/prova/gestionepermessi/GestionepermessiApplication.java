package it.prova.gestionepermessi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {
	
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(GestionepermessiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_CLASSIC_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Dipendente User", "ROLE_CLASSIC_USER"));
		}
		
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Back Office User", "ROLE_BO_USER"));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della password non lo 
		//faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Calogero", "Corsello", new Date());
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			utenteServiceInstance.inserisciNuovo(admin);
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("backoffice") == null) {
			Utente backOfficeUser = new Utente("backoffice", "backoffice", "Vincenzo", "Corsello", new Date());
			backOfficeUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER"));
			utenteServiceInstance.inserisciNuovo(backOfficeUser);
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(backOfficeUser.getId());
		}

		if (utenteServiceInstance.findByUsername("user1") == null) {
			Utente dipendenteUser1 = new Utente("user1", "user1", "Flavio", "Amato", new Date());
			dipendenteUser1.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
			utenteServiceInstance.inserisciNuovo(dipendenteUser1);
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(dipendenteUser1.getId());
		}

		if (utenteServiceInstance.findByUsername("user2") == null) {
			Utente dipendenteUser2 = new Utente("user2", "user2", "Emanuele", "Seminara", new Date());
			dipendenteUser2.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
			utenteServiceInstance.inserisciNuovo(dipendenteUser2);
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(dipendenteUser2.getId());
		}
	}
}