package it.prova.gestionepermessi.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import it.prova.gestionepermessi.dto.UtenteToShowDTO;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.repository.messaggio.MessaggioRepository;
import it.prova.gestionepermessi.repository.utente.UtenteRepository;

@Component
public class CustomAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private MessaggioRepository messaggioRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//voglio mettere in sessione uno userInfo perché spring security mette solo un principal da cui attingere username
		Utente utenteFromDb = utenteRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Username " + authentication.getName() + " not found"));
		UtenteToShowDTO utenteInPagina = new UtenteToShowDTO();
		utenteInPagina.setNomeDipendente(utenteFromDb.getDipendente().getNome());
		utenteInPagina.setCognomeDipendente(utenteFromDb.getDipendente().getCognome());
		request.getSession().setAttribute("userInfo", utenteInPagina);
		if(utenteFromDb.isBackOffice()) {
			request.getSession().setAttribute("unread_messages", messaggioRepository.countByLetto(false));
		}
		response.sendRedirect("home");	
	}
}