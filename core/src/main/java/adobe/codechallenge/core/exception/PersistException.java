package adobe.codechallenge.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * PersistException
 * ----------------
 * A Custom Exception which is thrown due to any issues while persisting the data in JCR
 */
public class PersistException extends Exception{
	
	private static final long serialVersionUID = -3884549265578895113L;

	public PersistException(String message)  {
		super(message);
	}
}
