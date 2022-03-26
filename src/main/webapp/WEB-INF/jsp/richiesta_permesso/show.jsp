<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza elemento</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
			
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Tipo permesso:</dt>
						<dd class="col-sm-9">${show_richiesta_attr.tipoPermesso}</dd>
					</dl>
					
					<dl class="row">
					    <dt class="col-sm-3 text-right">Data inizio:</dt>
						<fmt:formatDate value="${show_richiesta_attr.dataInizio}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
						<dd class="col-sm-9">${ theFormattedDate }</dd>
					</dl>
					
					<dl class="row">
					    <dt class="col-sm-3 text-right">Data fine:</dt>
						<fmt:formatDate value="${show_richiesta_attr.dataFine}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
						<dd class="col-sm-9">${ theFormattedDate }</dd>
					</dl>
					
					<dl class="row">
						<dt class="col-sm-3 text-right">Stato approvazione richiesta:</dt>
						<dd class="col-sm-9">${show_richiesta_attr.approvato }</dd>
					</dl>
					
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Codice certificato:</dt>
					  <dd class="col-sm-9">${show_richiesta_attr.codiceCertificato }</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Note: </dt>
					  <dd class="col-sm-9">${show_richiesta_attr.note }</dd>
			    	</dl>
			    	
<!-- 					info Regista -->
<!-- 			    	<p> -->
<!-- 					  <a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample"> -->
<!-- 					    Info Contribuente -->
<!-- 					  </a> -->
<!-- 					</p> -->
<!-- 					<div class="collapse" id="collapseExample"> -->
<!-- 					  <div class="card card-body"> -->
<!-- 					  	<dl class="row"> -->
<!-- 						  <dt class="col-sm-3 text-right">Nome:</dt> -->
<%-- 						  <dd class="col-sm-9">${show_cartella_attr.contribuente.nome}</dd> --%>
<!-- 					   	</dl> -->
<!-- 					   	<dl class="row"> -->
<!-- 						  <dt class="col-sm-3 text-right">Cognome:</dt> -->
<%-- 						  <dd class="col-sm-9">${show_cartella_attr.contribuente.cognome}</dd> --%>
<!-- 					   	</dl> -->
<!-- 					   	<dl class="row"> -->
<!-- 						  <dt class="col-sm-3 text-right">Codice fiscale:</dt> -->
<%-- 						  <dd class="col-sm-9">${show_cartella_attr.contribuente.codiceFiscale}</dd> --%>
<!-- 					   	</dl> -->
					    
<!-- 					  </div> -->
<!-- 					end info Regista -->
<!-- 					</div> -->
			    	<sec:authorize access="hasRole('BO_USER')">
				    	<!-- info Dipendente -->
				    	<p>
						  <a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
						    Info Dipendente
						  </a>
						</p>
						<div class="collapse" id="collapseExample">
						  <div class="card card-body">
						  	<dl class="row">
							  <dt class="col-sm-3 text-right">Nome:</dt>
							  <dd class="col-sm-9">${show_richiesta_attr.dipendente.nome}</dd>
						   	</dl>
						   	<dl class="row">
							  <dt class="col-sm-3 text-right">Cognome:</dt>
							  <dd class="col-sm-9">${show_richiesta_attr.dipendente.cognome}</dd>
						   	</dl>
						   	<dl class="row">
							  <dt class="col-sm-3 text-right">Codice fiscale:</dt>
							  <dd class="col-sm-9">${show_richiesta_attr.dipendente.codFis}</dd>
						   	</dl>
						    
						  </div>
						<!-- end info Dipendente -->
						</div>
			    	</sec:authorize>
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer'>
			        <a href="${pageContext.request.contextPath }/richiesta_permesso" class='btn btn-outline-secondary' style='width:80px'>
			            <i class='fa fa-chevron-left'></i> Back
			        </a>
			 	    <sec:authorize access="hasRole('BO_USER')">
				        <jsp:useBean id="now" class="java.util.Date"/>
				        <c:if test ="${show_richiesta_attr.dataInizio.after(now) }" >
				        	<a id="changeStatoLink_#_${show_richiesta_attr.id }" class="btn btn-outline-${show_richiesta_attr.isApprovato()?'danger':'success'} btn-sm link-for-modal" data-bs-toggle="modal" data-bs-target="#confirmOperationModal"  >${show_richiesta_attr.isApprovato()?'Disapprova':'Approva'}</a>
				        </c:if>
				    </sec:authorize>
			    </div>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		</div>
		
	</main>
	<jsp:include page="../footer.jsp" />
	
		<!-- Modal -->
	<div class="modal fade" id="confirmOperationModal" tabindex="-1"  aria-labelledby="confirmOperationModalLabel"
	    aria-hidden="true">
	    <div class="modal-dialog" >
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="confirmOperationModalLabel">Conferma Operazione</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <div class="modal-body">
	                Continuare con l'operazione?
	            </div>
	            <form method="post" action="${pageContext.request.contextPath}/richiesta_permesso/cambiaStato " >
		            <div class="modal-footer">
		            	<input type="hidden" name="idRichiestaForChangingStato" id="idRichiestaForChangingStato">
		                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
		                <input type="submit" value="Continua"  class="btn btn-primary">
		            </div>
	            </form>
	        </div>
	    </div>
	</div>
	<!-- end Modal -->
		<script type="text/javascript">
		<!-- aggancio evento click al conferma del modal  -->
		$(".link-for-modal").click(function(){
			<!-- mi prendo il numero che poi sarà l'id. Il 18 è perché 'changeStatoLink_#_' è appunto lungo 18  -->
			var callerId = $(this).attr('id').substring(18);
			<!-- imposto nell'hidden del modal l'id da postare alla servlet -->
			$('#idRichiestaForChangingStato').val(callerId);
		});
		</script>
	
</body>
</html>