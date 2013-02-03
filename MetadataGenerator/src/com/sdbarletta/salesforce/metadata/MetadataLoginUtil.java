package com.sdbarletta.salesforce.metadata;

import com.sforce.soap.partner.PartnerConnection;
import javax.jws.WebMethod;
import javax.jws.WebService;
import com.sforce.soap.partner.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * Provides the ability to login and logout of a salesforce instance.
 * 
 * @author Sandy Barletta
 *
 */
@WebService
public class MetadataLoginUtil {
	public static final String TEST_LOGIN_ENDPOINT = "https://test.salesforce.com/services/Soap/u/26.0";
    public static final String PROD_LOGIN_ENDPOINT = "https://login.salesforce.com/services/Soap/u/26.0"; 
    
    private PartnerConnection partnerConnection = null;
    
    public MetadataLoginUtil() {}
    
    @WebMethod
    public void logout() throws Exception {
    	if(partnerConnection != null) {
    		partnerConnection.logout();
    		partnerConnection = null;
    	}
	}
   
    /**
     * Login to Saleforce.com
     * 
     * @param username
     * @param password
     * @param loginUrl
     * @return ConnectorConfig
     * @throws ConnectionException
     */
    @WebMethod
	public MyConnectorConfig loginToSalesforce(final String username,
										     final String password,
										     final String loginUrl) throws Exception {
    	logout();
		ConnectorConfig c1 = new ConnectorConfig();
		c1.setAuthEndpoint(loginUrl);
		c1.setServiceEndpoint(loginUrl);
		c1.setManualLogin(true);
		c1.setCompression(true);
		
		partnerConnection = new PartnerConnection(c1);
		LoginResult lr = partnerConnection.login(username, password);
		partnerConnection.getConfig().setServiceEndpoint(lr.getServerUrl());
		partnerConnection.setSessionHeader(lr.getSessionId());
		MyConnectorConfig c2 = new MyConnectorConfig();
		c2.authEndpoint = loginUrl;
		c2.serviceEndpoint = lr.getMetadataServerUrl();
		c2.sessionId = lr.getSessionId();
		c2.manualLogin = true;
		c2.compression = true;
		return c2;
	}
    
    public static class MyConnectorConfig {
    	public String authEndpoint;
    	public String serviceEndpoint;
    	public String sessionId;
    	public boolean manualLogin;
    	public boolean compression;
    }
	
}
