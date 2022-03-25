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

		<title>Ricerca messaggi</title>
	    
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
			
								<form method="post" action="list" class="row g-3">
									
									<div class="col-md-6">
										<label for="oggetto" class="form-label">Oggetto </label>
										<input type="text" name="oggetto" id="oggetto" class="form-control" placeholder="Inserire l'oggetto">
									</div>	
									
									<div class="col-md-6">
										<label for="testo" class="form-label">Testo </label>
										<input type="text" name="testo" id="testo" class="form-control" placeholder="Inserire il testo">
									</div>	
									
									<div class="row-md-6">
										<p>Stato:</p>
										<div class="form-check">
										  <input class="form-check-input" type="radio" name="letto" id="flexRadioDefault1" value="" checked>
										  <label class="form-check-label" for="flexRadioDefault1">
										    Tutti
										  </label>
									    </div>
									
										<div class="form-check">
											<input class="form-check-input" type="radio" name="letto" id="flexRadioDefault2" value="true">
											  <label class="form-check-label" for="flexRadioDefault2">
											    Letti
											  </label>
										</div>
										
										<div class="form-check">
											<input class="form-check-input" type="radio" name="letto" id="flexRadioDefault3" value="false">
											  <label class="form-check-label" for="flexRadioDefault3">
											    Non letti
											  </label>
										</div>
									</div>
								
									<div class="col-12">	
										<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
										<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
									</div>
										
									
								</form>
					    
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