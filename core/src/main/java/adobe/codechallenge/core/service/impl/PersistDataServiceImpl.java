package adobe.codechallenge.core.service.impl;

import java.util.Map;
import java.util.UUID;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adobe.codechallenge.core.exception.PersistException;
import adobe.codechallenge.core.service.PersistDataService;
import adobe.codechallenge.core.util.DateUtil;

import com.day.cq.commons.jcr.JcrUtil; 

/**
 * PersistDataServiceImpl
 * ----------------------
 * This class is used to Persist the Response Data in to JCR as UUID
 * The class accepts the responseData and Post it to the Endpoint to 
 * receive the response
 * 
 * note- The formPersistPath needs to be created before hand.
 */
@Component(service = PersistDataService.class,property = {
        "formPersistPath=\\content\\form\\persist\\adobecodechallenge\\"
    })
public class PersistDataServiceImpl implements PersistDataService{
	private String FORM_PERSIST_PATH = "";
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@SlingObject
	private ResourceResolver resourceResolver;
	
	/**
	 * @method persistData - This method will receive the Response as String
	 * and then use JCR Operations to persist below the formPersistPath 
	 * configuration
	 */
	@Override
	public boolean persistData(String responseData) throws PersistException, Exception {
		boolean status = false;
		try {
			Session session = resourceResolver.adaptTo(Session.class);
			Node formPersistPath = session.getNode(FORM_PERSIST_PATH); 
			/*Check if the formPersistPath exists.*/
			if(formPersistPath == null) {
				logger.error("Missing Path in JCR:"+FORM_PERSIST_PATH);
				throw new PersistException("Missing Path in JCR:"+FORM_PERSIST_PATH);
			}
	        Node formResponseDataNode = JcrUtil.createPath(formPersistPath.getPath() + DateUtil.getCurrentDateTime(),"nt:unstructured", session); 
	        formResponseDataNode.setProperty("response", UUID.fromString(responseData).toString()); 
	        session.save();
	        status=true;
		} catch(RepositoryException re) {
			logger.error("Couldn't persist the node",re);
			throw new PersistException(re.getMessage());
		}
		return status;
	}

	@Activate
    protected void activate(Map<String, Object> properties) {

        this.FORM_PERSIST_PATH = Converters.standardConverter()
                .convert(properties.get("formPersistPath"))
                .to(String.class);

        logger.info("Activated PersistDataServiceImpl with formPersistPath [ {} ]", FORM_PERSIST_PATH);
    }

    @Deactivate
    protected void deactivate() {
        logger.info("PersistDataServiceImpl has been deactivated!");
    }
}
