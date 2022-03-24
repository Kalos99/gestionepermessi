package it.prova.gestionepermessi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "utente")
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "dateCreated")
	private Date dateCreated;

	// se non uso questa annotation viene gestito come un intero
	@Enumerated(EnumType.STRING)
	private StatoUtente stato;

	@ManyToMany
	@JoinTable(name = "utente_ruolo", joinColumns = @JoinColumn(name = "utente_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ruolo_id", referencedColumnName = "ID"))
	private Set<Ruolo> ruoli = new HashSet<>(0);
	
	@OneToOne(mappedBy = "utente")
	@NotNull
	private Dipendente dipendente;
	
	public Utente() {
	}

	public Utente(Long id, String username, String password, String nome, String cognome, Date dateCreated,
			StatoUtente stato, Set<Ruolo> ruoli, Dipendente dipendente) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoli = ruoli;
		this.dipendente = dipendente;
	}

	public Utente(Long id, StatoUtente stato, Set<Ruolo> ruoli) {
		this.id = id;
		this.stato = stato;
		this.ruoli = ruoli;
	}

	public Utente(StatoUtente stato, Set<Ruolo> ruoli) {
		this.stato = stato;
		this.ruoli = ruoli;
	}

	public Utente(Long id, String username, String password, Date dateCreated, StatoUtente stato, Set<Ruolo> ruoli) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoli = ruoli;
	}

	public Utente(String username, String password, Date dateCreated, StatoUtente stato, Set<Ruolo> ruoli) {
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.stato = stato;
		this.ruoli = ruoli;
	}

	public Utente(String username, String password, Date dateCreated, StatoUtente stato) {
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

	

	public Utente(Long id, String username, String password, Date dateCreated, StatoUtente stato) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
		this.stato = stato;
	}

	public Utente(String username, String password, Date dateCreated) {
		this.username = username;
		this.password = password;
		this.dateCreated = dateCreated;
	}

	public Utente(Date dateCreated) {
		this.dateCreated = dateCreated;
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

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public Dipendente getDipendente() {
		return dipendente;
	}

	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}

	public boolean isAdmin() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_ADMIN))
				return true;
		}
		return false;
	}
	
	public boolean isBackOffice() {
		for (Ruolo ruoloItem : ruoli) {
			if (ruoloItem.getCodice().equals(Ruolo.ROLE_BO_USER))
				return true;
		}
		return false;
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public boolean isDisabilitato() {
		return this.stato != null && this.stato.equals(StatoUtente.DISABILITATO);
	}
}