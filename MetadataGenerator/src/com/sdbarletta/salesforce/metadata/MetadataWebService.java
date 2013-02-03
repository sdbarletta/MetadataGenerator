package com.sdbarletta.salesforce.metadata;

import com.sforce.soap.metadata.*;

import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import com.sdbarletta.salesforce.util.Base64;

import javax.jws.WebMethod;
import javax.jws.WebService;

import java.util.logging.Logger;

/**
 * Salesforce metadata methods
 * 
 * @author Sandy Barletta
 *
 */
@WebService
public class MetadataWebService {
	private static final Logger log = Logger.getLogger(MetadataWebService.class.getName());
	
	// One second in milliseconds
	private static final int ONE_SECOND = 1000;
	private static final int TWO_MINUTES = (120 * ONE_SECOND);
	
	public MetadataWebService() {}
	
	/**
	 * Login to the target Salesforce instance using credentials
	 * 
	 * @param sessionId
	 * @param serviceEndpoint
	 * 
	 * @throws ConnectionException
	 */
	private MetadataConnection createMetadataConnection(final String sessionId, final String serviceEndpoint) throws ConnectionException {
		log.info("*** Entering createMetadataConnection");
		MetadataConnection mdc = null;
		final ConnectorConfig config = new ConnectorConfig();
		config.setSessionId(sessionId);
		config.setServiceEndpoint(serviceEndpoint);
		config.setConnectionTimeout(TWO_MINUTES);
		config.setTraceMessage(true);
		mdc = new MetadataConnection(config);
		log.info("*** mdc is "+(mdc == null? "null": "not null"));
		return mdc;
	}
	
	/**
	 * Create a single Visualforce page
	 * 
	 * @param uniqueName
	 * @param label
	 * @param apiVersion
	 * @param content
	 * @throws Exception
	 */
	@WebMethod
	public MetadataResult createVisualforcePage(final String sessionId, final String serviceEndpoint,
												final String uniqueName, final String label, final double apiVersion, final String content) {
		log.info("*** Entering createVisualforcePage");
		MetadataResult result = new MetadataResult("createVisualforcePage");
		try {
			MetadataConnection mdc = createMetadataConnection(sessionId, serviceEndpoint);
			
			ApexPage thePage = new ApexPage();
			thePage.setFullName(uniqueName);
			thePage.setDescription("Created by the SFMetadataGenerator");
			thePage.setLabel(label);
			thePage.setApiVersion(apiVersion);
			thePage.setContent(Base64.decode(content));
			
			AsyncResult[] asyncResults = mdc.create(new ApexPage[]{thePage});
			if (asyncResults != null && asyncResults.length > 0) {
			
				long waitTimeMilliSecs = ONE_SECOND;
				
				// After the create() call completes, we must poll the results of the checkStatus()
				// call until it indicates that the create operation has completed.
				do {
					printAsyncResultStatus(asyncResults);
					waitTimeMilliSecs *= 2;
					Thread.sleep(waitTimeMilliSecs);
					asyncResults = mdc.checkStatus(new String[]{asyncResults[0].getId()});
				} while (!asyncResults[0].isDone());
				
				printAsyncResultStatus(asyncResults);
			}
			log.info("*** createVisualforcePage created "+uniqueName);
			result.success = true;
		} catch(Exception e) {
			result.errorMessage = new String[1];
			result.errorMessage[0] = e.getMessage();
			log.warning("*** createVisualforcePage failed: "+result.errorMessage[0]);
		}
		
		return result;
	}

	private void printAsyncResultStatus(AsyncResult[] asyncResults) throws Exception {
		AsyncResult asyncResult = asyncResults[0]; //we are creating only 1 metadata object
		if (asyncResult.getStatusCode() != null) {
			throw new Exception("Error status code: "  + asyncResult.getStatusCode());
		}
	}
	
	/**
	 * Create a set of Visualforce pages
	 * 
	 * @param uniqueName
	 * @param label
	 * @param apiVersion
	 * @param content
	 */
	@WebMethod
	public MetadataResult[] createVisualforcePages(final String sessionId, final String serviceEndpoint,
												   final String[] uniqueName, final String[] label, final double apiVersion, final String[] content) {
		log.info("*** Entering createVisualforcePages");
		MetadataResult[] result = new MetadataResult[uniqueName.length];
		MetadataResult currResult = null;
		
		for(int i=0; i < uniqueName.length; i++) {
			try {
				currResult = new MetadataResult("createVisualforcePages "+uniqueName[i]);
				createVisualforcePage(sessionId, serviceEndpoint, uniqueName[i], label[i], apiVersion, content[i]);
				currResult.success = true;
				log.info("*** createVisualforcePages created "+uniqueName[i]);
			} catch(Exception e) {
				currResult.errorMessage = new String[1];
				currResult.errorMessage[0] = e.getMessage();
				log.warning("*** createVisualforcePages failed: "+currResult.errorMessage[0]);
			} finally {
				result[i] = currResult;
			}
		}
		
		return result;
	}
	
	/**
	 * Ping the web service
	 */
	@WebMethod
	public String ping() {
		return "Hello, the SFMetadataGenerator is up and running!";
	}
	
}

