package adobe.codechallenge.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.request.RequestParameterMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.haf.annotations.HttpRequestParam;

import adobe.codechallenge.core.exception.ValidationException;
import adobe.codechallenge.core.service.EventRegistrationService;
import adobe.codechallenge.core.service.HTTPPostService;
import adobe.codechallenge.core.service.PersistDataService;
import adobe.codechallenge.core.service.ValidationService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/*@Component(service = Servlet.class, property = {
    "sling.servlet.methods=" + HttpConstants.METHOD_POST,
    "sling.servlet.resourceTypes=sling/servlet/default",
    "sling.servlet.selectors=formHandler",
    "sling.servlet.extensions=html"
})*/
@Component(service = { Servlet.class })
@SlingServletResourceTypes(
		resourceTypes="sling/servlet/default",
//        resourceTypes="adobecodechallenge/components/page",
        methods=HttpConstants.METHOD_POST,
        selectors = "formHandler",
        extensions="html")
@ServiceDescription("A servlet to receive Form Submissions and Process the request")
public class FormSubmissionServlet  extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static String errorAllParamsMissing = "Please fill all the fields on the Form";
	private final static String errorInternalServer = "Internal Server Error, Please try again later";
	
	@Reference
	private EventRegistrationService eventRegistration;
	
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        logger.error("Forms Submission Received");
        RequestParameterMap parameters =  request.getRequestParameterMap();
        if(parameters.size() < 1) {
        	response.sendError(422, errorAllParamsMissing);
        	return;
        }
        try {
        	eventRegistration.registerEvent(parameters);
        } catch(ValidationException ve) {
        	response.sendError(422,ve.getMessage());
    	}	catch(Exception e) {
        	response.sendError(500,errorInternalServer);
        }
    }
}