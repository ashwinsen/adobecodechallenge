package adobe.codechallenge.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
    "sling.servlet.methods=" + HttpConstants.METHOD_POST,
    "sling.servlet.resourceTypes=sling/servlet/default",
    "sling.servlet.selectors=formHandler",
    "sling.servlet.extensions=html"
})
public class FormSubmissionServlet  extends SlingAllMethodsServlet {

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Handling form request via servlet");
    }
}