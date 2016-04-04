package com.github.ldaniels528.exams.ui;

import static java.lang.String.*;

/**
 * Represents an Content Loading Exception
 * @author lawrence.daniels@gmail.com
 */
@SuppressWarnings("serial")
class ContentLoadException extends RuntimeException {
	
	ContentLoadException(final String resourcePath) {
		super( format( "Resource '%s' was not found", resourcePath ) );
	}
	
	ContentLoadException(final String resourcePath, Exception e) {
		super( format( "Resource '%s' was not found", resourcePath ), e );
	}

}
