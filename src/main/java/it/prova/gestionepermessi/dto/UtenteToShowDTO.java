package it.prova.gestionepermessi.dto;

public class UtenteToShowDTO {
	
	private String nomeDipendente;
	
	private String cognomeDipendente;

	public UtenteToShowDTO() {
	}

	public UtenteToShowDTO(String nomeDipendente, String cognomeDipendente) {
		this.nomeDipendente = nomeDipendente;
		this.cognomeDipendente = cognomeDipendente;
	}

	public String getNomeDipendente() {
		return nomeDipendente;
	}

	public void setNomeDipendente(String nomeDipendente) {
		this.nomeDipendente = nomeDipendente;
	}

	public String getCognomeDipendente() {
		return cognomeDipendente;
	}

	public void setCognomeDipendente(String cognomeDipendente) {
		this.cognomeDipendente = cognomeDipendente;
	}
}
