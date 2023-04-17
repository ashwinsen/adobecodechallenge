package adobe.codechallenge.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ValidationException
 * -------------------
 * A Custom Exception which is thrown to notify Validation Errors
 */
public class ValidationException extends Exception{
	
	private static final long serialVersionUID = -5954063575625784740L;

	public ValidationException(String message)  {
		super(message);
	}
}
