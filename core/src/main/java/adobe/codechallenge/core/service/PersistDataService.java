package adobe.codechallenge.core.service;

import adobe.codechallenge.core.exception.PersistException;
import adobe.codechallenge.core.models.EventData;

/**
 * 
 * PersistDataService
 * -----------------
 * This service will be responsible for persisting the Response Data to JCR
 */
public interface PersistDataService {
	public boolean persistData(String responseData) throws PersistException, Exception;
}
