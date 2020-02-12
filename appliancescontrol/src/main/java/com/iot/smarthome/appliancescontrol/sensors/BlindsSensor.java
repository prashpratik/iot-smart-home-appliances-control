package com.iot.smarthome.appliancescontrol.sensors;

import java.io.IOException;
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
 * This is a class for creating Blinds Sensor and
 * implementing a CoAP Server for it
 */
@Component
public class BlindsSensor {
	
	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public BlindsSensor() {
		
		System.out.println("========================================");
		System.out.println("Blinds Sensor Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5688);

		server.add(new GetBlindsStatus());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Blinds Sensor Started");
		System.out.println("========================================");
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to get Blinds Status
	 */
	public static class GetBlindsStatus extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public GetBlindsStatus() {
        	
            super("getBlindsStatus");
            
            getAttributes().setTitle("Get Blinds Status");
        }

        /**
         * This method handles the GET request for Blinds Status and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handleGET(CoapExchange exchange) { 
        	
        	String blindsData="";
        	
        	// Checks if file exists and if not then, creates a file with default values
        	if(!Files.exists(Paths.get("EnvironmentData/Blinds.txt")))
        	{
        		try {
        			if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}
        			String defaultData = "DOWN";
        			Files.write(Paths.get("EnvironmentData/Blinds.txt"), defaultData.getBytes());
    	        }

    	        catch (IOException e) {
    	            System.err.format("IOException: %s%n", e);
    	        }       		
        	}

        	// Reads values from file
        	try {  
        		blindsData = new String(Files.readAllBytes(Paths.get("EnvironmentData/Blinds.txt")));
            } 
            
            catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        	
        	// Sends response to GET request
        	JSONObject json = new JSONObject().put("BlindsStatus",blindsData);
            exchange.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }
	}

}
