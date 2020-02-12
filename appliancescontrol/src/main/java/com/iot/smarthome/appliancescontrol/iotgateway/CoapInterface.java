package com.iot.smarthome.appliancescontrol.iotgateway;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * This is a class for CoAP Interface of IoT Gateway where
 * CoAP Client is implemented. Here, GET requests and 
 * POST requests are called for CoAP Servers
 */
@Component
public class CoapInterface {

	/**
     * This method calls the GET request and
     * sends response for it
     * @param url URL of CoAP Server
     * @return JSONObject Returns data in form of JSON Object
     * @throws Exception in case of invalid value 
     */
	public JSONObject getCoapClient(String url) throws Exception
	{	
		JSONObject jsondata = new JSONObject();
		
		CoapClient client = new CoapClient(url);	    
		CoapResponse response = client.get();	    
		if (response!=null) {
			jsondata = new JSONObject(response.getResponseText());
		}	    
		else {        	
			System.out.println("Request failed");        	
		}

		return jsondata;	    
	}
	
	/**
     * This method calls the POST request and
     * sends response for it
     * @param url URL of CoAP Server
     * @param postData Data to be posted
     * @throws Exception in case of invalid value 
     */
	public void postCoapClient(String url, String postData) throws Exception
	{		
		CoapClient client = new CoapClient(url);	    
		CoapResponse response = client.post(postData, MediaTypeRegistry.APPLICATION_JSON);
        
        if (response!=null) {
        	response.getResponseText();        	
        }        
        else {
        	System.out.println("Request failed");       	
        }
	}

}
