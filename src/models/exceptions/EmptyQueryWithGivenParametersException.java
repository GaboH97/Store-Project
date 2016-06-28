package models.exceptions;

public class EmptyQueryWithGivenParametersException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyQueryWithGivenParametersException() {
		super("Query gave any displayable result");
	}
}
