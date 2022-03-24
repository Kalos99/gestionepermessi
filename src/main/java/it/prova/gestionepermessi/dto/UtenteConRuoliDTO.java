package it.prova.gestionepermessi.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.Ruolo;
import it.prova.gestionepermessi.model.StatoUtente;
import it.prova.gestionepermessi.model.Utente;
import it.prova.gestionepermessi.validation.ValidationNoPassword;
import it.prova.gestionepermessi.validation.ValidationWithPassword;

public class UtenteConRuoliDTO {
	
	private Long id;

	@NotBlank(message = "{username.notblank}", groups = { ValidationWithPassword.class, ValidationNoPassword.class })
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}", groups = ValidationWithPassword.class)
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;

	private Date dateCreated;

	private StatoUtente stato;

	private Set<RuoloDTO> ruoli = new HashSet<RuoloDTO>();
	
	private DipendenteDTO dipendente;

	public UtenteConRuoliDTO() {
	}
	
	public UtenteConRuoliDTO(Long id, String username, String password, String confermaPassword, Date dateCreated, StatoUtente stato, Set<RuoloDTO> ruoli, DipendenteDTO dipendente) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.confermaPassword = confermaPassword;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoli = ruoli;
		this.dipendente = dipendente;
	}



	public UtenteConRuoliDTO(Long id, String username, StatoUtente stato, Set<RuoloDTO> ruoli) {
		super();
		this.id = id;
		this.username = username;
		this.stato = stato;
		this.ruoli = ruoli;
	}

	public UtenteConRuoliDTO(Long id, String username, Date dateCreated, StatoUtente stato, Set<RuoloDTO> ruoli) {
		this.id = id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoli = ruoli;
	}	

	public UtenteConRuoliDTO(Long id, String username, Date dateCreated, StatoUtente stato) {
		this.id = id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

	public Set<RuoloDTO> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<RuoloDTO> ruoli) {
		this.ruoli = ruoli;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public DipendenteDTO getDipendente() {
		return dipendente;
	}

	public void setDipendente(DipendenteDTO dipendente) {
		this.dipendente = dipendente;
	}

	public Utente buildUtenteModel(boolean includeIdRoles, boolean includeDipendente) {
		Utente result = new Utente(this.id, this.username, this.password, this.dateCreated, this.stato);
		if (includeIdRoles && ruoli != null)
			result.setRuoli(Arrays.asList(ruoli).stream().map(id -> new Ruolo()).collect(Collectors.toSet()));
		
		if(includeDipendente && dipendente.getId() != null)
			result.setDipendente(dipendente.buildDipendenteModel());

		return result;
	}

	// niente password...
	public static UtenteConRuoliDTO buildUtenteConRuoliDTOFromModel(Utente utenteModel) {
		UtenteConRuoliDTO result = new UtenteConRuoliDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getDateCreated(), utenteModel.getStato());

		if (!utenteModel.getRuoli().isEmpty())
			result.ruoli = RuoloDTO.createRuoloDTOListFromModelList(utenteModel.getRuoli().stream().collect(Collectors.toList())).stream().collect(Collectors.toSet());
;

		return result;
	}
	
	public static List<UtenteConRuoliDTO> createUtenteConRuoliDTOListFromModelList(List<Utente> modelListInput) {
		return modelListInput.stream().map(utenteEntity -> {
			return UtenteConRuoliDTO.buildUtenteConRuoliDTOFromModel(utenteEntity);
		}).collect(Collectors.toList());
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}
}