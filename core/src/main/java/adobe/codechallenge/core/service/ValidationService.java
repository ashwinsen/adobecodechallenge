package adobe.codechallenge.core.service;

import adobe.codechallenge.core.exception.ValidationException;
import adobe.codechallenge.core.models.EventData;

public interface ValidationService {
	public void validate(EventData eventData) throws ValidationException;
}
