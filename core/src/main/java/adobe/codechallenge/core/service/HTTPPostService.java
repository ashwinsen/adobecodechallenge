package adobe.codechallenge.core.service;

import adobe.codechallenge.core.models.EventData;

public interface HTTPPostService {
	public void postData(EventData data) throws Exception;
}
