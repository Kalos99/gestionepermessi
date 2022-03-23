package it.prova.gestionepermessi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "richiestapermesso_id", referencedColumnName = "id", nullable = false, unique = true)
	private RichiestaPermesso richiesta;

	public Messaggio() {
	}

	public Messaggio(Long id, String testo, String oggetto, Boolean letto, RichiestaPermesso richiesta) {
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.richiesta = richiesta;
	}

	public Messaggio(Long id, String testo, String oggetto, Boolean letto) {
		this.id = id;
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
	}

	public Messaggio(String testo, String oggetto, Boolean letto, RichiestaPermesso richiesta) {
		this.testo = testo;
		this.oggetto = oggetto;
		this.letto = letto;
		this.richiesta = richiesta;
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

	public RichiestaPermesso getRichiesta() {
		return richiesta;
	}

	public void setRichiesta(RichiestaPermesso richiesta) {
		this.richiesta = richiesta;
	}
}