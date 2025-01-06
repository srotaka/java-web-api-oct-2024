package ar.com.educacionit.repository.exceptions;

public class DBConnectionException extends RuntimeException {

	public DBConnectionException(String message, Throwable cause) {
		super(message,cause);
	}

}
