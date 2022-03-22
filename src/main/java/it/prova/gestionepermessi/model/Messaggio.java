package it.prova.gestionepermessi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "messaggio")
public class Messaggio {

	@Column(name = "id")
	private Long id;
	@Column(name = "testo")
	private String testo;
	@Column(name = "oggetto")
	private String oggetto;
	@Column(name = "letto")
	private Boolean letto = false;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utenteInserimento;

	public Messaggio() {
	}

	public Messaggio(Long id, String testo, String oggetto, Boolean letto, Utente utenteInserimento) {
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.utenteInserimento = utenteInserimento;
	}

	public Messaggio(Long id, String testo, String oggetto, Boolean letto) {
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
	}

	public Messaggio(String testo, String oggetto, Boolean letto, Utente utenteInserimento) {
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.utenteInserimento = utenteInserimento;
	}

	public Messaggio(String testo, String oggetto, Boolean letto) {
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public Boolean getLetto() {
		return letto;
	}

	public void setLetto(Boolean letto) {
		this.letto = letto;
	}

	public Utente getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(Utente utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}
}