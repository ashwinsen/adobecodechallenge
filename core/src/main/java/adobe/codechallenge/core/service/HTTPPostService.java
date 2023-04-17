package adobe.codechallenge.core.service;

import org.apache.commons.httpclient.HttpException;

import adobe.codechallenge.core.models.EventData;

public interface HTTPPostService {
	public String postData(EventData data) throws HttpException, Exception;
}
