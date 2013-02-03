package com.sdbarletta.salesforce.util;

import com.sforce.ws.tools.wsdlc;

public class Wsdl2Jar {

	/**
	 * Use this program to build a Java jar from a Salesforce wsdl
	 * @param args
	 */
	public static void main(String[] args) {
		args = new String[2];
		
		// Build the metadata jar
		//args[0] = "C:\\Users\\Sandy Barletta\\Desktop\\Exp\\HSAM\\metadata_ecmv1.wsdl";
		//args[1] = "C:\\Users\\Sandy Barletta\\Desktop\\Exp\\HSAM\\metadata_26.jar";
		// OR
		// Build the partner jar
		//args[0] = "C:\\Users\\Sandy Barletta\\Desktop\\Exp\\HSAM\\ecmv1_partner.wsdl";
		//args[1] = "C:\\Users\\Sandy Barletta\\Desktop\\Exp\\HSAM\\partner_26.jar";
		// OR
		// Build the enterprise jar
		//args[0] = "C:\\Users\\Sandy Barletta\\Desktop\\Exp\\HSAM\\ecmv1_enterprise.wsdl";
		//args[1] = "C:\\Users\\Sandy Barletta\\Desktop\\Exp\\HSAM\\enterprise_26.jar";
		
		try {
			wsdlc.main(args);
			System.out.println("Success");
		} catch(Exception e) {
			System.err.print(e.getMessage());
		}
	}

}
