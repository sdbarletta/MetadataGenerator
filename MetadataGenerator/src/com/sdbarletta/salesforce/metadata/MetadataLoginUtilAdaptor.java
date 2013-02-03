package com.sdbarletta.salesforce.metadata;

import com.sdbarletta.salesforce.metadata.MetadataLoginUtil;
import com.sdbarletta.salesforce.metadata.jaxws.*;
import com.sdbarletta.salesforce.metadata.MetadataLoginUtil.MyConnectorConfig;

/**
 * Intermediary between the soap handler and the web service
 * 
 * @author Sandy Barletta
 *
 */
public class MetadataLoginUtilAdaptor {
	private MetadataLoginUtil ws = new MetadataLoginUtil();
	
	public LoginToSalesforceResponse loginToSalesforce(LoginToSalesforce request) throws Exception{
		String username = request.getArg0();
		String password = request.getArg1();
		String loginUrl = request.getArg2();
		MyConnectorConfig config = ws.loginToSalesforce(username, password, loginUrl);
		LoginToSalesforceResponse response = new LoginToSalesforceResponse();
		response.setReturn(config);
		return response;
	}
	
	public LogoutResponse logout(Logout request) throws Exception{
		ws.logout();
		LogoutResponse response = new LogoutResponse();
		return response;
	}
	
}

