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
							  <dt class="col-sm-3 text-right">Oggetto:</dt>
							  <dd class="col-sm-9">${ show_messaggio_attr.oggetto }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Testo:</dt>
							  <dd class="col-sm-9">${ show_messaggio_attr.testo }</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Stato lettura messaggio:</dt>
							  <dd class="col-sm-9">${show_messaggio_attr.letto}</dd>
					    	</dl>
					    	
					    </div>
					    
					    <div class='card-footer'>
					        <a href="${ pageContext.request.contextPath }/messaggio" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back
					        </a>
					        <a href="${ pageContext.request.contextPath }/richiesta_permesso/show/${show_messaggio_attr.richiesta.id}" class='btn btn-outline-primary' style='width:150px'>
					            <i class='fa fa-chevron-left'></i> Vai alla richiesta
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