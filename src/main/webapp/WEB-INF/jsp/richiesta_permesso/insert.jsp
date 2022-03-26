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
	    <style>
			.ui-autocomplete-loading {
				background: white url("../assets/img/jqueryUI/anim_16x16.gif") right center no-repeat;
			}
			.error_field {
		        color: red; 
		    }
		</style>
		<title>Inserisci nuova richiesta</title>
	    
	</head>
	<body class="d-flex flex-column h-100">
		<jsp:include page="../navbar.jsp" />
		
		<!-- Begin page content -->
		<main class="flex-shrink-0">
			<div class="container">
			
					<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="insert_richiesta_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
				
					<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					
					<div class='card'>
					    <div class='card-header'>
					        <h5>Inserisci nuova richiesta di permesso</h5> 
					    </div>
					    <div class='card-body'>
					    
					   		 <h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
			
								<form:form method="post" modelAttribute="insert_richiesta_attr" action="save" novalidate="novalidate" class="row g-3">
									<input type="hidden" name="usernameUtente" value= <sec:authentication property = "principal.username"/> >
								
									<div class="col-md-12">
										<label for="tipoPermesso" class="form-label">Tipo permesso <span class="text-danger">*</span></label>
										<spring:bind path="tipoPermesso">
											<select class="form-select  ${status.error ? 'is-invalid' : ''}" id="tipoPermesso" name="tipoPermesso" required>
										    	<option value="" selected> - Selezionare - </option>
										   		<option value="FERIE" ${insert_richiesta_attr.tipoPermesso == 'FERIE'?'selected':''}>FERIE</option>
										   		<option value="MALATTIA" ${insert_richiesta_attr.tipoPermesso == 'MALATTIA'?'selected':''}>MALATTIA</option>
											</select>
										</spring:bind>
										<form:errors  path="tipoPermesso" cssClass="error_field" />
									</div>
									
									<div class="col-md-12 d-none" id="certificato">
										<label for="codiceCertificato" class="form-label">Codice certificato <span class="text-danger">*</span></label>
										<spring:bind path="codiceCertificato">
											<input type="text" name="codiceCertificato" id="codiceCertificato" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il codice" value="${insert_richiesta_attr.codiceCertificato }" required>
										</spring:bind>
										<form:errors  path="codiceCertificato" cssClass="error_field" />
									</div>
									
<!-- 									<div class="col-md-6 d-none" id="allegato"> -->
<!-- 										<label for="attachment" class="form-label">Allegato <span class="text-danger">*</span></label> -->
<%-- 										<spring:bind path="attachment"> --%>
<%-- 											<input type="file" name="attachment" id="attachment" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="caricare il file"  required> --%>
<%-- 										</spring:bind> --%>
<%-- 										<form:errors  path="codiceCertificato" cssClass="error_field" /> --%>
<!-- 									</div>						 -->
									
									<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${insert_richiesta_attr.dataInizio}' />
									<div class="col-md-6">
										<label for="dataInizio" class="form-label">Data inizio <span class="text-danger">*</span></label>
	                        			<spring:bind path="dataInizio">
		                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataInizio" type="date" placeholder="dd/MM/yy"
		                            		title="formato : gg/mm/aaaa"  name="dataInizio" required 
		                            		value="${parsedDate}" >
			                            </spring:bind>
		                            	<form:errors  path="dataInizio" cssClass="error_field" />
									</div>
									
									<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDate" type='date' value='${insert_richiesta_attr.dataFine}' />
									<div class="col-md-6">
										<label for="dataFine" class="form-label">Data fine <span class="text-danger">*</span></label>
	                        			<spring:bind path="dataFine">
		                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataFine" type="date" placeholder="dd/MM/yy"
		                            		title="formato : gg/mm/aaaa"  name="dataFine" required 
		                            		value="${parsedDate}" >
			                            </spring:bind>
		                            	<form:errors  path="dataFine" cssClass="error_field" />
									</div>
									
									<span>Note </span>
									<div class="form-floating col-md-12">
									  <textarea name="note" id="note" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserisci eventuali note" style="height: 100px"></textarea>
									  <label for="note">Inserire eventuali note: </label>
									</div>
																	
									<div class="col-12">	
										<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
										<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
										<a class="btn btn-outline-primary ml-2" href="${pageContext.request.contextPath}/richiesta_permesso/insert">Add New</a>
									</div>
										
									
								</form:form>
								
<%-- 								FUNZIONE JQUERY UI PER SHOW/HIDE CAMPI AGGIUNTIVI --%>


										<script>
										$('.form-select').click(function(){
											
											if($('#tipoPermesso :selected').text()=== 'MALATTIA'){
												//console.log("MALATTIA");
												$("#certificato").removeClass('d-none');
												$("#allegato").removeClass('d-none');
											}else{
												//console.log("FERIE");
												$("#certificato").addClass('d-none');
												$("#allegato").addClass('d-none');
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