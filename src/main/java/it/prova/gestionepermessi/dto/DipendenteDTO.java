package it.prova.gestionepermessi.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.gestionepermessi.model.Dipendente;
import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.Sesso;

public class DipendenteDTO {
	
	private Long id;

	@NotBlank(message = "{nome.notblank}")
	private String nome;
	
	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotBlank(message = "{codFis.notblank}")
	private String codFis;

	@NotBlank(message = "{email.notblank}")
	private String email;

	@NotNull(message = "{dataNascita.notnull}")
	private Date dataNascita;

	@NotNull(message = "{dataAssunzione.notnull}")
	private Date dataAssunzione;

	private Date dataDimissioni;

	@NotBlank(message = "{sesso.notblank}")
	private Sesso sesso;

	private Set<RichiestaPermessoDTO> richieste = new HashSet<RichiestaPermessoDTO>(0);

	public DipendenteDTO() {
	}

	public DipendenteDTO(Long id) {
		this.id = id;
	}

	public DipendenteDTO(Long id, String nome, String cognome, String codFis, String email, Date dataNascita, Date dataAssunzione, Date dataDimissioni, Sesso sesso, Set<RichiestaPermessoDTO> richieste) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
		this.richieste = richieste;
	}

	public DipendenteDTO(Long id, String nome, String cognome, String codFis, String email, Date dataNascita, Date dataAssunzione, Date dataDimissioni, Sesso sesso) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
	}

	public DipendenteDTO(String nome, String cognome, String codFis, String email, Date dataNascita, Date dataAssunzione, Date dataDimissioni, Sesso sesso, Set<RichiestaPermessoDTO> richieste) {
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
		this.richieste = richieste;
	}

	public DipendenteDTO(String nome, String cognome, String codFis, String email, Date dataNascita, Date dataAssunzione, Date dataDimissioni, Sesso sesso) {
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.dataDimissioni = dataDimissioni;
		this.sesso = sesso;
	}

	public DipendenteDTO(String nome, String cognome, String codFis, String email, Date dataNascita, Date dataAssunzione, Sesso sesso) {
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.sesso = sesso;
	}

	public DipendenteDTO(Long id, String nome, String cognome, String codFis, String email, Date dataNascita, Date dataAssunzione, Sesso sesso, Set<RichiestaPermessoDTO> richieste) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.sesso = sesso;
		this.richieste = richieste;
	}

	public DipendenteDTO(Long id, String nome, String cognome, String codFis, String email, Date dataNascita, Date dataAssunzione, Sesso sesso) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codFis = codFis;
		this.email = email;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.sesso = sesso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodFis() {
		return codFis;
	}

	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}

	public Date getDataDimissioni() {
		return dataDimissioni;
	}

	public void setDataDimissioni(Date dataDimissioni) {
		this.dataDimissioni = dataDimissioni;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}

	public Set<RichiestaPermessoDTO> getRichieste() {
		return richieste;
	}

	public void setRichieste(Set<RichiestaPermessoDTO> richieste) {
		this.richieste = richieste;
	}
	
	public Dipendente buildDipendenteModel() {
		Dipendente result = new Dipendente(this.id, this.nome, this.cognome, this.codFis, this.email, this.dataNascita, this.dataAssunzione, this.dataDimissioni, this.sesso);
		if (richieste != null)
			result.setRichieste(Arrays.asList(richieste).stream().map(id -> new RichiestaPermesso()).collect(Collectors.toSet()));

		return result;
	}

	// niente password...
	public static DipendenteDTO buildDipendenteDTOFromModel(Dipendente dipendenteModel) {
		DipendenteDTO result = new DipendenteDTO(dipendenteModel.getId(), dipendenteModel.getNome(), dipendenteModel.getCognome(), dipendenteModel.getCodFis(), dipendenteModel.getEmail(), dipendenteModel.getDataNascita(), dipendenteModel.getDataAssunzione(), dipendenteModel.getDataDimissioni(), dipendenteModel.getSesso());

		if (!dipendenteModel.getRichieste().isEmpty())
			result.richieste = RichiestaPermessoDTO.createRichiestaPermessoDTOListFromModelList(dipendenteModel.getRichieste().stream().collect(Collectors.toList())).stream().collect(Collectors.toSet());
		return result;
	}
	
	public static List<DipendenteDTO> createDipendenteDTOListFromModelList(List<Dipendente> modelListInput) {
		return modelListInput.stream().map(dipendenteEntity -> {
			return DipendenteDTO.buildDipendenteDTOFromModel(dipendenteEntity);
		}).collect(Collectors.toList());
	}
}