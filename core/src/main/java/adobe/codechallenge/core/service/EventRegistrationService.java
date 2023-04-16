package adobe.codechallenge.core.service;

import org.apache.sling.api.request.RequestParameterMap;

import adobe.codechallenge.core.exception.ValidationException;

public interface EventRegistrationService {
	public String registerEvent(RequestParameterMap parameters) throws ValidationException,Exception;

}
