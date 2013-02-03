package com.sdbarletta.salesforce.metadata;

public class MetadataResult {
	public String operationName;
	public boolean success=false;
	public String[] errorMessage;
	public String result;
	
	public MetadataResult(String name) {
		operationName = name;
	}
	
	public MetadataResult(){}
}
