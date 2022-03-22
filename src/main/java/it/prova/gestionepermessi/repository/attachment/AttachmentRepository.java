package it.prova.gestionepermessi.repository.attachment;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionepermessi.model.Attachment;

public interface AttachmentRepository extends PagingAndSortingRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment>{

}
