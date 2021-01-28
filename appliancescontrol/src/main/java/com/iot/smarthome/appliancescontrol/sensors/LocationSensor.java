package com.iot.smarthome.appliancescontrol.sensors;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * This is a class for creating Location Sensor and
 * implementing a CoAP Server for it
 */
@Component
public class LocationSensor {
	
	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public LocationSensor() {
		
		System.out.println("========================================");
		System.out.println("Location Sensor Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5690);

		server.add(new GetLocation());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Location Sensor Started");
		System.out.println("========================================");
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to get Location
	 */
	public static class GetLocation extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public GetLocation() {
        	
            super("getLocation");
            
            getAttributes().setTitle("Get Location");
        }
        
        // Initializing counter for the number of hits
        int locationData = 0;

        /**
         * This method handles the GET request for Location and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handleGET(CoapExchange exchange) { 
        	
        	// Increases the previous data for each hit
        	try {
				locationData++;
				
				if (locationData > 100)
				{
					locationData = 0;
				}
				
				// Sends response to GET request
				JSONObject json = new JSONObject().put("Location",locationData);
				exchange.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON);
			} 
        	
        	catch (Exception e) {
	            System.err.format("Exception: %s%n", e);
	        }
        }
    }

}
