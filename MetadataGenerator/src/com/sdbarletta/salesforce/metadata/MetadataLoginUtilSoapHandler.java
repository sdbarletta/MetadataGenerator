package com.sdbarletta.salesforce.metadata;

import java.util.Iterator;
import javax.xml.bind.JAXB;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SAAJResult;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import com.sdbarletta.salesforce.metadata.jaxws.*;

/**
 * Handles incoming soap requests and directs them to the correct web service.
 * 
 * @author Sandy Barletta
 *
 */
public class MetadataLoginUtilSoapHandler {

  private static final String NAMESPACE_URI = "http://metadata.salesforce.sdbarletta.com/";
  private static final QName LOGIN_TO_SALESFORCE_QNAME = new QName(NAMESPACE_URI, "loginToSalesforce");
  private static final QName LOGOUT_QNAME = new QName(NAMESPACE_URI, "logout");

  private MessageFactory messageFactory;
  private MetadataLoginUtilAdaptor wsAdapter;

  public MetadataLoginUtilSoapHandler() throws SOAPException {
    messageFactory = MessageFactory.newInstance();
    wsAdapter = new MetadataLoginUtilAdaptor();
  }

  public SOAPMessage handleSOAPRequest(SOAPMessage request) throws SOAPException, Exception {
    SOAPBody soapBody = request.getSOAPBody();
    Iterator iterator = soapBody.getChildElements();
    Object responsePojo = null;
    while (iterator.hasNext()) {
      Object next = iterator.next();
      if (next instanceof SOAPElement) {
        SOAPElement soapElement = (SOAPElement) next;
        QName qname = soapElement.getElementQName();
        if (LOGIN_TO_SALESFORCE_QNAME.equals(qname)) {
        	responsePojo = handleLoginRequest(soapElement);
        	break;
        } else if (LOGOUT_QNAME.equals(qname)) {
        	responsePojo = handleLogoutRequest(soapElement);
        	break;
        }
      }
    }
    
    SOAPMessage soapResponse = messageFactory.createMessage();
    soapBody = soapResponse.getSOAPBody();
    if (responsePojo != null) {
      JAXB.marshal(responsePojo, new SAAJResult(soapBody));
    } else {
      SOAPFault fault = soapBody.addFault();
      fault.setFaultString("Unrecognized SOAP request.");
    }
    return soapResponse;
  }

 
  private Object handleLoginRequest(SOAPElement soapElement) throws Exception {
    LoginToSalesforce loginRequest = JAXB.unmarshal(new DOMSource(soapElement), LoginToSalesforce.class);
    return wsAdapter.loginToSalesforce(loginRequest);
  }
  
  private Object handleLogoutRequest(SOAPElement soapElement) throws Exception {
	  Logout logoutRequest = JAXB.unmarshal(new DOMSource(soapElement), Logout.class);
	  return wsAdapter.logout(logoutRequest);
  }
}


