package adobe.codechallenge.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameterMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adobe.codechallenge.core.exception.ValidationException;
import adobe.codechallenge.core.service.EventRegistrationService;

/**
 * FormSubmissionServlet
 * ---------------------
 * This is a generic Form Post Service Servlet which accepts
 * a Form request and performs registrations. It connects with the
 * EventRegistrationService to perform this service
 *
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(resourceTypes = "sling/servlet/default", methods = HttpConstants.METHOD_POST, selectors = "formHandler", extensions = "html")
@ServiceDescription("A servlet to receive Form Submissions and Process the request")
public class FormSubmissionServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1173558431599737701L;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final static String errorAllParamsEmpty = "Please fill all the fields on the Form";
	private final static String errorParamsEmpty = " fields are empty";
	private final static String errorInternalServer = "Internal Server Error, Please try again later";
	private final static String errorInternalServerPost = "Internal Server Error, Unable to post to 3rd Party Rest Endpoint.Please try again later";
	private final static String errorInternalServerSave = "Internal Server Error, Unable to Persist.Please try again later";
	private final static String successMessage = "Form Successfully Submitted and Received";

	@Reference
	private EventRegistrationService eventRegistration;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Forms Submission Received");
		RequestParameterMap parameters = request.getRequestParameterMap();
		
		/*Check if the parameters are not Empty*/
		if (parameters.size() < 1) {
			logger.error("Validation Exception : " + errorAllParamsEmpty);
			response.sendError(422, errorAllParamsEmpty);
			return;
		}
		
		/* Call the EventRegistrationService to register the Event */
		try {
			
			eventRegistration.registerEvent(parameters);
			
		} catch (ValidationException ve) {
			
			/* ValidationException is thrown if any fields are empty*/
			logger.error("ValidationException ", ve);
			response.sendError(422, ve.getMessage() + errorParamsEmpty);
			
		} catch (HttpException he) {
			
			/* HttpException is thrown if 3rd Party Service is not accessible*/
			logger.error("Http Post Exception", he);
			response.sendError(500, errorInternalServerPost);
			
		} catch (RepositoryException re) {
			
			/* Repository Exception is thrown if the data cannot be persisted*/
			logger.error("Http Post Exception", re);
			response.sendError(500, errorInternalServerSave);
			
		} catch (Exception e) {
			
			logger.error("Exception ", e);
			response.sendError(500, errorInternalServer);
		}
		PrintWriter out = response.getWriter();
		out.println(successMessage);
	}
}