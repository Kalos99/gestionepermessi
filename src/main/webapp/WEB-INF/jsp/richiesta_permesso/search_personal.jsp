<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
	<head>
		<jsp:include page="../header.jsp" />
		
	    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/jqueryUI/jquery-ui.min.css" />

		<title>Ricerca richieste permesso</title>
	    
	</head>
	<body class="d-flex flex-column h-100">
		<jsp:include page="../navbar.jsp" />
		
		<!-- Begin page content -->
		<main class="flex-shrink-0">
			<div class="container">
				
					<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					
					<div class='card'>
					    <div class='card-header'>
					        <h5>Inserisci i dati per la ricerca</h5> 
					    </div>
					    <div class='card-body'>
			
								<form method="post" action="list_personal/${principal.username }" class="row g-3">
									<input type="hidden" name="usernameUtente" value= <sec:authentication property = "principal.username"/> >	
								
									<div class="col-md-12">
										<label for="tipoPermesso" class="form-label">Tipo permesso </label>
										<select class="form-select" id="tipoPermesso" name="tipoPermesso">
									    	<option value="" selected> - Selezionare - </option>
									   		<option value="FERIE" >FERIE</option>
									   		<option value="MALATTIA" >MALATTIA</option>
										</select>
									</div>
									
									<div class="col-md-12 d-none" id="certificato">
										<label for="codiceCertificato" class="form-label">Codice certificato </label>
										<input type="text" name="codiceCertificato" id="codiceCertificato" class="form-control" placeholder="Inserire il codice">
									</div>	
									
									<div class="col-md-6">
										<label for="dataInizio" class="form-label">Data Inizio</label>
		                        		<input class="form-control" id="dataInizio" type="date" placeholder="dd/MM/yy"
		                            		title="formato : gg/mm/aaaa"  name="dataInizio" >
									</div>
							
									<div class="col-md-6">
										<label for="dataFine" class="form-label">Data Fine</label>
		                        		<input class="form-control" id="dataFine" type="date" placeholder="dd/MM/yy"
		                            		title="formato : gg/mm/aaaa"  name="dataFine" >
									</div>
									
									<div class="row-md-6">
										<p>Stato:</p>
										<div class="form-check">
										  <input class="form-check-input" type="radio" name="approvato" id="flexRadioDefault1" value="" checked>
										  <label class="form-check-label" for="flexRadioDefault1">
										    Tutte
										  </label>
									    </div>
									
										<div class="form-check">
											<input class="form-check-input" type="radio" name="approvato" id="flexRadioDefault2" value="true">
											  <label class="form-check-label" for="flexRadioDefault2">
											    Approvate
											  </label>
										</div>
										
										<div class="form-check">
											<input class="form-check-input" type="radio" name="approvato" id="flexRadioDefault3" value="false">
											  <label class="form-check-label" for="flexRadioDefault3">
											    Non approvate
											  </label>
										</div>
									</div>
								
									<div class="col-12">	
										<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
										<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
										<a class="btn btn-outline-primary ml-2" href="${pageContext.request.contextPath}/richiesta_permesso/insert">Add New</a>
									</div>
										
									
								</form>

<%-- 								FUNZIONE JQUERY UI PER SHOW/HIDE CAMPI AGGIUNTIVI --%>


										<script>
										$('.form-select').change(function(){
											console.log("ciao");
											if($('#tipoPermesso :selected').text()=== 'MALATTIA'){
												$("#certificato").removeClass('d-none');
											}else{
												$("#certificato").addClass('d-none');
											}
										});

	 								</script>
<!-- 								end script 	 -->
					    
						<!-- end card-body -->			   
					    </div>
					<!-- end card -->
					</div>
				<!-- end container -->
				</div>	
		
		<!-- end main -->	
		</main>
		<jsp:include page="../footer.jsp" />
		
	</body>
</html>