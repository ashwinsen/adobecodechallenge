package adobe.codechallenge.core.service;

import javax.jcr.RepositoryException;

import org.apache.commons.httpclient.HttpException;
import org.apache.sling.api.request.RequestParameterMap;

import adobe.codechallenge.core.exception.ValidationException;
/**
 * EventRegistrationService
 * ------------------------
 * This service is responsible for orchestrating the overall flow for the Form Submission, by calling the 
 * appropriate service for Validation, Posting to 3rd party Service & Finally persisting the response in JCR 
 *
 */
public interface EventRegistrationService {
	public void registerEvent(RequestParameterMap parameters) throws ValidationException,HttpException, RepositoryException, Exception;

}
