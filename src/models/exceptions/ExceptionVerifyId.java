package models.exceptions;

public class ExceptionVerifyId extends Exception{
	private static final long serialVersionUID = 1L;

	public ExceptionVerifyId() {
		super("el id ya existe");
	}

}
