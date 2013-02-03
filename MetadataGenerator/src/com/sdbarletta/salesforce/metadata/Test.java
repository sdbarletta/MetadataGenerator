package com.sdbarletta.salesforce.metadata;

import com.sdbarletta.salesforce.util.Base64;
import com.sdbarletta.salesforce.metadata.MetadataWebService;

public class Test {

	public static void main(String[] args) {
		MetadataLoginUtil util = new MetadataLoginUtil();
		
		try {
			MetadataWebService ws = new MetadataWebService();
			System.out.println(ws.ping());
			MetadataLoginUtil.MyConnectorConfig config = util.loginToSalesforce("ecm_metadata_api_user@exponentpartners.com", "ecm2013!", MetadataLoginUtil.TEST_LOGIN_ENDPOINT);
			System.out.println("Login succeeded.");
			String content = "<apex:page>Hello World</apex:page>";
			MetadataResult r = ws.createVisualforcePage(config.sessionId, config.serviceEndpoint, "AAA2", "AAA 2", 26.0, Base64.encodeBytes(content.getBytes()));
			if(r.success) {
				System.out.println("Page successfully created.");
				util.logout();
				System.out.println("Logout succeeded.");
			} else {
				System.err.println(r.operationName+" failed. "+r.errorMessage[0]);
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		} 
	}
}
