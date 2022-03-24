<!doctype html>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Visualizza Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Visualizza dettaglio</h5>
					    </div>
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Username:</dt>
							  <dd class="col-sm-9">${ show_utente_attr.username }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data creazione utente:</dt>
							  <fmt:formatDate value="${show_utente_attr.dateCreated}" type="date" pattern="dd/MM/yyyy" var="theFormattedDate" />
							  <dd class="col-sm-9">${ theFormattedDate }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Stato:</dt>
							  <dd class="col-sm-9">${show_utente_attr.stato}</dd>
					    	</dl>
					    	
					    <!-- info Ruoli -->
			                <p>
				              <a class="btn btn-outline-primary btn-sm" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
				               	 Info Ruoli
				              </a>
				            </p>
				            
				            <div class="collapse" id="collapseExample">
				              <div class="card card-body">
				                 <dl class= "row">
				                  <dt class = "col-sm-3 text-right"> Ruoli:</dt>
				                  <c:forEach items="${show_utente_attr.ruoli}" var="ruolo">
				                    <dd class="row-sm-9">${ ruolo.codice }  ${ruolo.descrizione }</dd>
				                    <br>
				                  </c:forEach>
				                </dl>
				                  
				               </div>
				             <!-- end info Ruoli -->
				             </div>
					    	
					    </div>
					    
					    <div class='card-footer'>
					        <a href="${ pageContext.request.contextPath }/utente" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back
					        </a>
					    </div>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>