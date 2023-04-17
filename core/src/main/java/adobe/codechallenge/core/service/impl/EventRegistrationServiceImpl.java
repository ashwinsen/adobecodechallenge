package adobe.codechallenge.core.service.impl;

import javax.jcr.RepositoryException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.request.RequestParameterMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adobe.codechallenge.core.exception.ValidationException;
import adobe.codechallenge.core.models.EventData;
import adobe.codechallenge.core.service.EventRegistrationService;
import adobe.codechallenge.core.service.HTTPPostService;
import adobe.codechallenge.core.service.PersistDataService;
import adobe.codechallenge.core.service.ValidationService;

/**
 * EventRegistrationServiceImpl
 * ----------------------------
 * This class is the orchestrator responsible for the overall flow of this implementation. 
 * This class calls the ValidationService to Validate the Form Data, Calls the HTTPPostService
 * to post the Form data to 3rd Party Service and Finally the response is persisted by calling
 * the PersistDataService.
 *
 */
@Component(service = EventRegistrationService.class)
public class EventRegistrationServiceImpl implements EventRegistrationService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Reference
	private HTTPPostService httpPostService;
	
	@Reference
	private PersistDataService persistDataService;
	
	@Reference
	private ValidationService validationService;

	/**
	 * @method registerEvent - This method calls the different service to Validate the FormData, 
	 * post the data to 3rd party Rest Service and finally persisting the data in JCR
	 */
	@Override
	public void registerEvent(RequestParameterMap parameters) throws ValidationException, HttpException,RepositoryException, Exception{
		logger.info("Register Event Start");
		EventData eventData = new EventData(parameters);
		validationService.validate(eventData);
		String response = httpPostService.postData(eventData);
		if(StringUtils.isEmpty(response)) {
			logger.error("No HttpPost response.The HttpPost Data has failed. Throwing HttpException");
			throw new HttpException();
		}
		persistDataService.persistData(response);
		logger.info("Register Event End");
	}
	
	
}
