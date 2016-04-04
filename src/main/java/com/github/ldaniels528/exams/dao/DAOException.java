package com.github.ldaniels528.exams.dao;

/**
 * Base Data Access Object Exception
 * @author lawrence.daniels@gmail.com
 */
@SuppressWarnings("serial")
class DAOException extends Exception {
	
	/**
	 * Creates a new DAO Exception
	 * @param cause the given {@link Exception cause}
	 */
	DAOException(final Exception cause) {
		super( cause );
	}

}
