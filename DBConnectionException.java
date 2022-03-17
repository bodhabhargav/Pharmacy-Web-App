package com.nttdata.casestudy.dbfw;

public class DBConnectionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBConnectionException(String mesg, Throwable cause) {

		super(mesg, cause);
	}

	public DBConnectionException(String mesg) {
		super(mesg);
	}
}
