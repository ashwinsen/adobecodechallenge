package adobe.codechallenge.core.service.impl;

import org.osgi.service.component.annotations.Component;

import adobe.codechallenge.core.models.EventData;
import adobe.codechallenge.core.service.HTTPPostService;

@Component(service = HTTPPostService.class)
public class HTTPPostServiceImpl implements HTTPPostService{

	@Override
	public void postData(EventData data) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
