<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Elimina richiesta permesso</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
	  		 <div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
				 ${errorMessage}
				 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			 </div>>
			
			<div class='card'>
			    <div class='card-header'>
			        Sei sicuro di voler eliminare questa richiesta?
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
						<dt class="col-sm-3 text-right">Tipo permesso:</dt>
						<dd class="col-sm-9">${delete_richiesta_attr.tipoPermesso}</dd>
					</dl>
					
					<dl class="row">
					    <dt class="col-sm-3 text-right">Data inizio:</dt>
						<fmt:formatDate value="${delete_richiesta_attr.dataInizio}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
						<dd class="col-sm-9">${ theFormattedDate }</dd>
					</dl>
					
					<dl class="row">
					    <dt class="col-sm-3 text-right">Data fine:</dt>
						<fmt:formatDate value="${delete_richiesta_attr.dataFine}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
						<dd class="col-sm-9">${ theFormattedDate }</dd>
					</dl>
					
					<dl class="row">
						<dt class="col-sm-3 text-right">Stato approvazione richiesta:</dt>
						<dd class="col-sm-9">${delete_richiesta_attr.approvato }</dd>
					</dl>
					
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Codice certificato:</dt>
					  <dd class="col-sm-9">${delete_richiesta_attr.codiceCertificato }</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Note: </dt>
					  <dd class="col-sm-9">${delete_richiesta_attr.note }</dd>
			    	</dl>
			    
			    </div>
			    
			    <form method="post" action="${pageContext.request.contextPath }/richiesta_permesso/remove" class="row g-3" novalidate="novalidate">
					  <div class='card-footer'>
					       <input type="hidden" name="idRichiesta" value="${delete_richiesta_attr.id}">
						   <button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
						   <a href="${pageContext.request.contextPath }/richiesta_permesso" class='btn btn-outline-secondary' style='width:80px'>
					          <i class='fa fa-chevron-left'></i> Back
					       </a>
							
					  	  </div>
						<!-- end card -->			  
			    	</form>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		</div>
		
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>