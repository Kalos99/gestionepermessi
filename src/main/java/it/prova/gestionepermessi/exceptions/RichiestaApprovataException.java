package it.prova.gestionepermessi.exceptions;

public class RichiestaApprovataException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public RichiestaApprovataException(String message) {
		super(message);
	}
}