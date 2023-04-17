package adobe.codechallenge.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.sling.api.request.RequestParameterMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import adobe.codechallenge.core.exception.ValidationException;

public class EventData {
	private String firstName;
	private String lastName;
	private String email;
	
	public EventData(RequestParameterMap parameters) {
		populateData(parameters);;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	private void populateData(RequestParameterMap parameters) {
		if(parameters == null) {
			return;
		}
		if(parameters.getValue("firstName") != null) {
			setFirstName(parameters.getValue("firstName").getString());
		}
		if(parameters.getValue("lastName") != null) {
			setLastName(parameters.getValue("lastName").getString());
		}
		if(parameters.getValue("email") != null) {
			setEmail(parameters.getValue("email").getString());
		}
	}
	
	public List<String> getAnyAttributesEmpty() throws ValidationException {
		List<String> missingDatas = new ArrayList<String>();
		if(StringUtils.isEmpty(getFirstName())) {
			missingDatas.add("firstName");
		}
		if(StringUtils.isEmpty(getLastName())) {
			missingDatas.add("lastName");
		}
		if(StringUtils.isEmpty(getEmail())) {
			missingDatas.add("email");
		}
		return missingDatas;
	}

	public List<NameValuePair> getNameValuePair() {
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("firstName", firstName));
		nameValuePair.add(new BasicNameValuePair("lastName", lastName));
		nameValuePair.add(new BasicNameValuePair("email", email));
		return nameValuePair;
	}
	
}
