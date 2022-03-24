package it.prova.gestionepermessi;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.Sesso;
import it.prova.gestionepermessi.service.DipendenteService;
import it.prova.gestionepermessi.service.RuoloService;
import it.prova.gestionepermessi.service.UtenteService;

@SpringBootApplication
public class GestionepermessiApplication implements CommandLineRunner {
	
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private DipendenteService dipendenteServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(GestionepermessiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Dipendente User", "ROLE_DIPENDENTE_USER"));
		}
		
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Back Office User", "ROLE_BO_USER"));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della password non lo 
		//faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("c.corsello") == null) {
			Date dataNascita1 = new SimpleDateFormat("dd/MM/yyyy").parse("20/06/1999");
			Date dataAssunzione1 = new SimpleDateFormat("dd/MM/yyyy").parse("10/02/2022");
			Dipendente admin = new Dipendente("Calogero", "Corsello",  "CRSCGR99H20B602J", dataNascita1, dataAssunzione1, Sesso.MASCHIO);
			
			dipendenteServiceInstance.inserisciNuovoECensisciUtente(admin, ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}
		
		if (utenteServiceInstance.findByUsername("v.corsello") == null) {
			Date dataNascita2 = new SimpleDateFormat("dd/MM/yyyy").parse("08/09/2000");
			Date dataAssunzione2 = new SimpleDateFormat("dd/MM/yyyy").parse("10/02/2022");
			Dipendente backOfficeUser = new Dipendente("Vincenzo", "Corsello",  " CRSVCN00P08B602N", dataNascita2, dataAssunzione2, Sesso.MASCHIO);
			
			dipendenteServiceInstance.inserisciNuovoECensisciUtente(backOfficeUser, ruoloServiceInstance.cercaPerDescrizioneECodice("Back Office User", "ROLE_BO_USER"));
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(backOfficeUser.getId());
		}
		
		if (utenteServiceInstance.findByUsername("f.amato") == null) {
			Date dataNascita3 = new SimpleDateFormat("dd/MM/yyyy").parse("02/12/1999");
			Date dataAssunzione3 = new SimpleDateFormat("dd/MM/yyyy").parse("10/02/2022");
			Dipendente dipendenteUser1 = new Dipendente("Flavio", "Amato",  " MTAFLV99T02A089R", dataNascita3, dataAssunzione3, Sesso.MASCHIO);
			
			dipendenteServiceInstance.inserisciNuovoECensisciUtente(dipendenteUser1, ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(dipendenteUser1.getId());
		}
		
		if (utenteServiceInstance.findByUsername("e.seminara") == null) {
			Date dataNascita4 = new SimpleDateFormat("dd/MM/yyyy").parse("19/05/1996");
			Date dataAssunzione4 = new SimpleDateFormat("dd/MM/yyyy").parse("10/02/2022");
			Dipendente dipendenteUser2 = new Dipendente("Emanuele", "Seminara", "SMNMNL96E19G273O", dataNascita4, dataAssunzione4, Sesso.MASCHIO);
			
			dipendenteServiceInstance.inserisciNuovoECensisciUtente(dipendenteUser2, ruoloServiceInstance.cercaPerDescrizioneECodice("Dipendente User", "ROLE_DIPENDENTE_USER"));
			//l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(dipendenteUser2.getId());
		}
	}
}