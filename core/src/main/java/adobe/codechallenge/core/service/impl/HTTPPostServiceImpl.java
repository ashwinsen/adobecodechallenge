package adobe.codechallenge.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adobe.codechallenge.core.models.EventData;
import adobe.codechallenge.core.service.HTTPPostService;
/**
 * HTTPPostServiceImpl
 * -------------------
 * This service is used to Post Data to any 3rd party Service.
 * This class uses Apache HttpPost client to interact with the Http Service
 *
 */
@Component(service = HTTPPostService.class,property = {
        "urlEndpoint=https://httpbin.org/"
    })
public class HTTPPostServiceImpl implements HTTPPostService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String URL_END_POINT = "";

	/**
	 * 
	 * @param properties
	 * 
	 * @method activate - This method overrides the activate and
	 *  initates the config properties to be used by this service. The urlEndpoint
	 *  is the target endpoint for the HttpService to post the request.
	 */
	@Activate
    protected void activate(Map<String, Object> properties) {

        this.URL_END_POINT = Converters.standardConverter()
                .convert(properties.get("urlEndpoint"))
                .to(String.class);

        logger.info("Activated HTTPPostServiceImpl with urlEndpoint [ {} ]", URL_END_POINT);
    }

	/**
	 * @method deactivate - Deactivate method of OSGI service
	 */
    @Deactivate
    protected void deactivate() {
        logger.info("HTTPPostServiceImpl has been deactivated!");
    }
    
    /**
     * @method postData - This method accepts the Form Data and 
     * submits the data to a 3rd party Service. The method uses appache HttpPost client
     * to post the data to the service.
     */
	@Override
	public String postData(EventData data) throws HttpException, Exception {
		HttpPost post = new HttpPost(URL_END_POINT);

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = data.getNameValuePair();

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			logger.error("Exception while posting Form Data to Rest Service", e);
			throw new HttpException();
		}
	}
}
