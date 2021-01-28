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
 * This is a class for creating Lamp Actuator and
 * implementing a CoAP Server for it
 */
@Component
public class LampActuator {
	
	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public LampActuator() {
		
		System.out.println("========================================");
		System.out.println("Lamp Actuator Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5695);

		server.add(new SetLampStatus());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Lamp Actuator Started");
		System.out.println("========================================");
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to set Lamp Status
	 */
	public static class SetLampStatus extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public SetLampStatus() {
        	
            super("setLampStatus");
            
            getAttributes().setTitle("Set Lamp Status");
        }

        /**
         * This method handles the POST request for Lamp Status and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handlePOST(CoapExchange exchange) {
        	
        	// Sets Lamp Status from POST request
        	try {
        		String data = "";
    			JSONObject json = new JSONObject(exchange.getRequestText());
    			data = json.getString("LampStatus");
    			
    			// Checks if file exists and if not then, creates a file
    			if(!Files.exists(Paths.get("EnvironmentData/Lamp.txt")))
            	{    				
    				if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}            		       		
            	}
    			
    			// Writes values into file
    			Files.write(Paths.get("EnvironmentData/Lamp.txt"), data.getBytes());
            	
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
