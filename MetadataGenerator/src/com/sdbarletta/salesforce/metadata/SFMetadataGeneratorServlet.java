package com.sdbarletta.salesforce.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * Servlet to handle incoming metadata soap requests
 * 
 * @author Sandy Barletta
 *
 */
@SuppressWarnings("serial")
public class SFMetadataGeneratorServlet extends HttpServlet {
	static MessageFactory messageFactory;
	static MetadataWebServiceSoapHandler soapHandler;
	
	static {
	    try {
	      messageFactory = MessageFactory.newInstance();
	      soapHandler = new MetadataWebServiceSoapHandler();
	    } catch (Exception ex) {
	        throw new RuntimeException(ex);
	    }
	}
	  
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    try {
	      // Get all the headers from the HTTP request
	      MimeHeaders headers = getHeaders(req);

	      // Construct a SOAPMessage from the XML in the request body
	      InputStream is = req.getInputStream();
	      SOAPMessage soapRequest = messageFactory.createMessage(headers, is);

	      // Handle soapReqest
	      SOAPMessage soapResponse = soapHandler.handleSOAPRequest(soapRequest);

	      // Write to HttpServeltResponse
	      resp.setStatus(HttpServletResponse.SC_OK);
	      resp.setContentType("text/xml;charset=\"utf-8\"");
	      OutputStream os = resp.getOutputStream();
	      soapResponse.writeTo(os);
	      os.flush();
	    } catch (SOAPException e) {
	    	throw new IOException(e.getMessage());
	    } catch (Exception e2) {
	    	throw new IOException(e2.getMessage());
	    }
	}
	
	static MimeHeaders getHeaders(HttpServletRequest req) {
	    Enumeration headerNames = req.getHeaderNames();
	    MimeHeaders headers = new MimeHeaders();
	    while (headerNames.hasMoreElements()) {
	      String headerName = (String) headerNames.nextElement();
	      String headerValue = req.getHeader(headerName);
	      StringTokenizer values = new StringTokenizer(headerValue, ",");
	      while (values.hasMoreTokens()) {
	        headers.addHeader(headerName, values.nextToken().trim());
	      }
	    }
	    return headers;
	}
}

