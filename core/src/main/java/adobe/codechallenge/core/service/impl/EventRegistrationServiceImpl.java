package adobe.codechallenge.core.service.impl;

import org.apache.sling.api.request.RequestParameterMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import adobe.codechallenge.core.exception.ValidationException;
import adobe.codechallenge.core.models.EventData;
import adobe.codechallenge.core.service.EventRegistrationService;
import adobe.codechallenge.core.service.HTTPPostService;
import adobe.codechallenge.core.service.PersistDataService;
import adobe.codechallenge.core.service.ValidationService;

@Component(service = EventRegistrationService.class)
public class EventRegistrationServiceImpl implements EventRegistrationService{
	@Reference
	private HTTPPostService httpPostService;
	
	@Reference
	private PersistDataService persistDataService;
	
	@Reference
	private ValidationService validationService;

	@Override
	public String registerEvent(RequestParameterMap parameters) throws ValidationException, Exception{
		EventData eventData = new EventData(parameters);
		validationService.validate(eventData);
		httpPostService
		return null;
	}
	
	
}
