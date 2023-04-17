package adobe.codechallenge.core.service.impl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import adobe.codechallenge.core.exception.ValidationException;
import adobe.codechallenge.core.models.EventData;
import adobe.codechallenge.core.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *  ValidationServiceImpl
 *  ---------------------
 *  This class accepts the missing Data from EventData and then 
 *  validates it to generate the ValidationException in case the data is missing.
 *  ValidationException will be processed in the higher layers to show appropriate 
 *  message to the customers
 */
@Component(service = ValidationService.class)
public class ValidationServiceImpl implements ValidationService{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void validate(EventData eventData) throws ValidationException{
		
		
		List<String> missingDatas = eventData.getAnyAttributesEmpty();
		if(missingDatas.size() > 0) {
			String missingDataItems = "";
			for(String missingItem:missingDatas) {
				missingDataItems = missingDataItems + " ," +missingItem;
			}
			missingDataItems = missingDataItems.substring(0, missingDataItems.length() - 2);
			logger.error(missingDataItems + " are Empty. Please fill these values and resubmit the form");
			throw new ValidationException(missingDataItems);
		}
	}

}
