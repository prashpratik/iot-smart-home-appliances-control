package com.iot.smarthome.appliancescontrol.sensors;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * This is a class for creating Light Sensor and
 * implementing a CoAP Server for it
 */
@Component
public class LightSensor {

	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public LightSensor() {
		
		System.out.println("========================================");
		System.out.println("Light Sensor Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5687);

		server.add(new GetLuminosity());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Light Sensor Started");
		System.out.println("========================================");
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to get Luminosity
	 */
	public static class GetLuminosity extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
		public GetLuminosity() {
        	
            super("getLuminosity");
            
            getAttributes().setTitle("Get Luminosity");
        }
        
		// Initializing counter for the number of hits and previous data
        int count = 0;
        int previousData = 0;

        /**
         * This method handles the GET request for Luminosity and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handleGET(CoapExchange exchange) { 
        	
        	// Creates the random data and saves it for some number of hits
        	try {
				int luminosityData = 0;
				
				count++;			
				if(count == 1)
				{
					luminosityData = (int)(Math.random()*101);
					previousData = luminosityData;
				}
				else if (count > 1 && count < 20)
				{
					luminosityData = previousData;
				}
				else 
				{
					luminosityData = previousData;
					count = 0;
				}        	
				     	
				// Sends response to GET request
				JSONObject json = new JSONObject().put("Luminosity",luminosityData);
				exchange.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON);
			} 
        	
        	catch (Exception e) {
	            System.err.format("Exception: %s%n", e);
	        }
        }
    }

}
