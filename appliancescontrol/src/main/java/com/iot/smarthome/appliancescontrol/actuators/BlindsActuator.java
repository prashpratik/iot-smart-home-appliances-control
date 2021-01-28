package com.iot.smarthome.appliancescontrol.actuators;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * This is a class for creating Blinds Actuator and
 * implementing a CoAP Server for it
 */
@Component
public class BlindsActuator {
	
	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public BlindsActuator() {
		
		System.out.println("========================================");
		System.out.println("Blinds Actuator Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5694);

		server.add(new SetBlindsStatus());       

		server.start();		

		System.out.println("----------------------------------------");
		System.out.println("Blinds Actuator Started");
		System.out.println("========================================");
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to set Blinds Status
	 */
	public static class SetBlindsStatus extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public SetBlindsStatus() {
        	
            super("setBlindsStatus");
            
            getAttributes().setTitle("Set Blinds Status");
        }

        /**
         * This method handles the POST request for Blinds Status and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handlePOST(CoapExchange exchange) {
        	
        	// Sets Blinds Status from POST request
        	try {
        		String data = "";
    			JSONObject json = new JSONObject(exchange.getRequestText());
    			data = json.getString("BlindsStatus");
    			
    			// Checks if file exists and if not then, creates a file
    			if(!Files.exists(Paths.get("EnvironmentData/Blinds.txt")))
            	{    				
    				if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}            		       		
            	}
    			
    			// Writes values into file
    			Files.write(Paths.get("EnvironmentData/Blinds.txt"), data.getBytes());
            	
    			// Sends response to POST request
            	JSONObject jsonResponse = new JSONObject().put("message","POST_REQUEST_SUCCESS");        	
            	exchange.respond(ResponseCode.CONTENT, jsonResponse.toString(), MediaTypeRegistry.APPLICATION_JSON);
	        }

	        catch (Exception e) {
	            System.err.format("Exception: %s%n", e);
	        }
        	
		}
        
    }

}
