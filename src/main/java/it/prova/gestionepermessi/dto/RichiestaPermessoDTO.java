package it.prova.gestionepermessi.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import it.prova.gestionepermessi.model.RichiestaPermesso;
import it.prova.gestionepermessi.model.TipoPermesso;

public class RichiestaPermessoDTO {
	
	private Long id;

	@NotNull(message = "{tipoPermesso.notblank}")
	private TipoPermesso tipoPermesso;

	@NotNull(message = "{dataInizio.notnull}")
	private Date dataInizio;

	@NotNull(message = "{dataFine.notnull}")
	private Date dataFine;

	private Boolean approvato;

	private String codiceCertificato;

	private String note;
	
	private AttachmentDTO attachment;
	
//	@NotNull(message = "{dipendente.notnull}")
	private DipendenteDTO dipendente;

	public RichiestaPermessoDTO() {
	}

	public RichiestaPermessoDTO(Long id) {
		this.id = id;
	}

	public RichiestaPermessoDTO(Long id, TipoPermesso tipoPermesso, Date dataInizio, Date dataFine, Boolean approvato, String codiceCertificato, String note, AttachmentDTO attachment, DipendenteDTO dipendente) {
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
		this.attachment = attachment;
		this.dipendente = dipendente;
	}

	public RichiestaPermessoDTO(TipoPermesso tipoPermesso, Date dataInizio, Date dataFine, Boolean approvato, String codiceCertificato, String note, AttachmentDTO attachment, DipendenteDTO dipendente) {
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
		this.attachment = attachment;
		this.dipendente = dipendente;
	}

	public RichiestaPermessoDTO(Long id, TipoPermesso tipoPermesso, Date dataInizio, Date dataFine, Boolean approvato, String codiceCertificato, String note, AttachmentDTO attachment) {
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
		this.attachment = attachment;
	}

	public RichiestaPermessoDTO(Long id, TipoPermesso tipoPermesso, Date dataInizio, Date dataFine, Boolean approvato, String codiceCertificato, String note) {
		this.id = id;
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
	}

	public RichiestaPermessoDTO(TipoPermesso tipoPermesso, Date dataInizio, Date dataFine, Boolean approvato, String codiceCertificato, String note) {
		this.tipoPermesso = tipoPermesso;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.approvato = approvato;
		this.codiceCertificato = codiceCertificato;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPermesso getTipoPermesso() {
		return tipoPermesso;
	}

	public void setTipoPermesso(TipoPermesso tipoPermesso) {
		this.tipoPermesso = tipoPermesso;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Boolean getApprovato() {
		return approvato;
	}

	public void setApprovato(Boolean approvato) {
		this.approvato = approvato;
	}

	public String getCodiceCertificato() {
		return codiceCertificato;
	}

	public void setCodiceCertificato(String codiceCertificato) {
		this.codiceCertificato = codiceCertificato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public AttachmentDTO getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDTO attachment) {
		this.attachment = attachment;
	}

	public DipendenteDTO getDipendente() {
		return dipendente;
	}

	public void setDipendente(DipendenteDTO dipendente) {
		this.dipendente = dipendente;
	}
	
	public boolean isApprovato() {
		return this.approvato;
	}
	
	public RichiestaPermesso buildRichiestaPermessoModel(boolean includesAttachment, boolean includesDipendente) {
		RichiestaPermesso result = new RichiestaPermesso(this.id, this.tipoPermesso, this.dataInizio, this.dataFine, this.approvato, this.codiceCertificato, this.note);
		
		if (includesAttachment && attachment.getId() != null)
			result.setAttachment(attachment.buildAttachmentModel());
		
		if (includesDipendente && dipendente.getId() != null)
			result.setDipendente(dipendente.buildDipendenteModel(true));
		return result;
	}

	public static RichiestaPermessoDTO buildRichiestaPermessoDTOFromModel(RichiestaPermesso richiestaModel) {
		RichiestaPermessoDTO result = new RichiestaPermessoDTO(richiestaModel.getId(), richiestaModel.getTipoPermesso(), richiestaModel.getDataInizio(),
				richiestaModel.getDataFine(), richiestaModel.getApprovato(), richiestaModel.getCodiceCertificato(), richiestaModel.getNote());

		if (richiestaModel.getDipendente() != null)
			result.setDipendente(DipendenteDTO.buildDipendenteDTOFromModel(richiestaModel.getDipendente()));

		return result;
	}

	public static List<RichiestaPermessoDTO> createRichiestaPermessoDTOListFromModelList(List<RichiestaPermesso> modelListInput) {
		return modelListInput.stream().map(richiestaEntity -> {
			return RichiestaPermessoDTO.buildRichiestaPermessoDTOFromModel(richiestaEntity);
		}).collect(Collectors.toList());
	}
}