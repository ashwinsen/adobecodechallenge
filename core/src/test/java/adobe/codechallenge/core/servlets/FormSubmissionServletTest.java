package adobe.codechallenge.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(AemContextExtension.class)
class FormSubmissionServletTest {

    private FormSubmissionServlet formSubmissionServlet = new FormSubmissionServlet();

    @Test
    void doPost(AemContext context) throws ServletException, IOException {
        context.build().resource("/content/test", "jcr:title", "resource title").commit();
        context.currentResource("/content/test");

        MockSlingHttpServletRequest request = context.request();
        request.setAttribute("firstName", "Tom");
        request.setAttribute("lastName", "Sawyer");
        request.setAttribute("email", "tom.sawyer@adobemock.com");
        MockSlingHttpServletResponse response = context.response();

        formSubmissionServlet.doPost(request, response);
        assertEquals("", response.getOutputAsString());
    }
}
