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
public class MetadataWebServiceSoapHandler {

  private static final String NAMESPACE_URI = "http://metadata.salesforce.sdbarletta.com/";
  private static final QName CREATE_VISUALFORCE_PAGE_QNAME = new QName(NAMESPACE_URI, "createVisualforcePage");
  private static final QName CREATE_VISUALFORCE_PAGES_QNAME = new QName(NAMESPACE_URI, "createVisualforcePages");
  private static final QName LOGIN_TO_SALESFORCE_QNAME = new QName(NAMESPACE_URI, "loginToSalesforce");
  private static final QName LOGOUT_QNAME = new QName(NAMESPACE_URI, "logout");
  private static final QName PING_QNAME = new QName(NAMESPACE_URI, "ping");
  
  private MessageFactory messageFactory;
  private MetadataWebServiceAdaptor wsAdapter;
  private MetadataLoginUtilAdaptor luAdapter;

  public MetadataWebServiceSoapHandler() throws SOAPException {
    messageFactory = MessageFactory.newInstance();
    wsAdapter = new MetadataWebServiceAdaptor();
    luAdapter = new MetadataLoginUtilAdaptor();
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
        if (CREATE_VISUALFORCE_PAGE_QNAME.equals(qname)) {
        	responsePojo = handleCreateVisualforcePageRequest(soapElement);
        	break;
        } else if (CREATE_VISUALFORCE_PAGES_QNAME.equals(qname)) {
        	responsePojo = handleCreateVisualforcePagesRequest(soapElement);
        	break;
        } else if (LOGIN_TO_SALESFORCE_QNAME.equals(qname)) {
        	responsePojo = handleLoginToSalesforceRequest(soapElement);
        	break;
        } else if (LOGOUT_QNAME.equals(qname)) {
        	responsePojo = handleLogoutRequest(soapElement);
        	break;
        } else if (PING_QNAME.equals(qname)) {
        	responsePojo = handlePingRequest(soapElement);
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

  private Object handleCreateVisualforcePageRequest(SOAPElement soapElement) throws Exception {
	  CreateVisualforcePage createVisualforcePageRequest = JAXB.unmarshal(new DOMSource(soapElement), CreateVisualforcePage.class);
	  return wsAdapter.createVisualforcePage(createVisualforcePageRequest);
  }

  private Object handleCreateVisualforcePagesRequest(SOAPElement soapElement) throws Exception {
	  CreateVisualforcePages createVisualforcePagesRequest = JAXB.unmarshal(new DOMSource(soapElement), CreateVisualforcePages.class);
	  return wsAdapter.createVisualforcePages(createVisualforcePagesRequest);
  }

  private Object handleLoginToSalesforceRequest(SOAPElement soapElement) throws Exception {
	  LoginToSalesforce request = JAXB.unmarshal(new DOMSource(soapElement), LoginToSalesforce.class);
	  return luAdapter.loginToSalesforce(request);
  }
 
  private Object handleLogoutRequest(SOAPElement soapElement) throws Exception {
	  Logout request = JAXB.unmarshal(new DOMSource(soapElement), Logout.class);
	  return luAdapter.logout(request);
  }
  
  private Object handlePingRequest(SOAPElement soapElement) throws Exception {
	  Ping request = JAXB.unmarshal(new DOMSource(soapElement), Ping.class);
	  return wsAdapter.ping(request);
  }
}

