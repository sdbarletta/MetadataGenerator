package com.sdbarletta.salesforce.metadata;

import com.sdbarletta.salesforce.metadata.MetadataWebService;
import com.sdbarletta.salesforce.metadata.jaxws.*;

/**
 * Intermediary between the soap handler and the web service
 * 
 * @author Sandy Barletta
 *
 */
public class MetadataWebServiceAdaptor {
	private MetadataWebService ws = new MetadataWebService();
	
	public CreateVisualforcePageResponse createVisualforcePage(CreateVisualforcePage request) throws Exception {
		String sessionId = request.getArg0();
		String serverEndPoint = request.getArg1();
		String uniqueName = request.getArg2();
	    String label = request.getArg3();
	    double apiVersion = request.getArg4();
	    String content = request.getArg5();
	    MetadataResult r = ws.createVisualforcePage(sessionId, serverEndPoint, uniqueName, label, apiVersion, content);
	    CreateVisualforcePageResponse response = new CreateVisualforcePageResponse();
	    response.setReturn(r);
	    return response;
	}
	
	public CreateVisualforcePagesResponse createVisualforcePages(CreateVisualforcePages request) throws Exception {
		String sessionId = request.getArg0();
		String serverEndPoint = request.getArg1();
		String[] uniqueName = request.getArg2();
	    String[] label = request.getArg3();
	    double apiVersion = request.getArg4();
	    String[] content = request.getArg5();
	    MetadataResult[] r = ws.createVisualforcePages(sessionId, serverEndPoint, uniqueName, label, apiVersion, content);
	    CreateVisualforcePagesResponse response = new CreateVisualforcePagesResponse();
	    response.setReturn(r);
	    return response;
	}
	
	public PingResponse ping(Ping request) throws Exception {
		String s = ws.ping();
		PingResponse response = new PingResponse();
		response.setReturn(s);
		return response;
	}
	
}
