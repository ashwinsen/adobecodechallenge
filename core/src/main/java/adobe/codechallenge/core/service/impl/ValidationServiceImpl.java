package adobe.codechallenge.core.service.impl;

import org.osgi.service.component.annotations.Component;

import adobe.codechallenge.core.models.EventData;
import adobe.codechallenge.core.service.ValidationService;

@Component(service = ValidationService.class)
public class ValidationServiceImpl implements ValidationService{

	@Override
	public EventData validate(EventData eventData) {
		// TODO Auto-generated method stub
		return null;
	}

}
