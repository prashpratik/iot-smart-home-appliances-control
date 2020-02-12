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
 * This is a class for creating Heater Sensor and
 * implementing a CoAP Server for it
 */
@Component
public class HeaterSensor {
	
	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public HeaterSensor() {
		
		System.out.println("========================================");
		System.out.println("Heater Sensor Starting");
		System.out.println("----------------------------------------");

		CoapServer server = new CoapServer(5686);

		server.add(new GetHeaterStatus());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Heater Sensor Started");
		System.out.println("========================================");
		
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to get Heater Status
	 */
	public static class GetHeaterStatus extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public GetHeaterStatus() {
        	
            super("getHeaterStatus");
            
            getAttributes().setTitle("Get Heater Status");
        }

        /**
         * This method handles the GET request for Heater Status and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handleGET(CoapExchange exchange) { 
        	
        	String heaterData="";
        	
        	// Checks if file exists and if not then, creates a file with default values
        	if(!Files.exists(Paths.get("EnvironmentData/Heater.txt")))
        	{
        		try {
        			if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}
        			String defaultData = "OFF";
        			Files.write(Paths.get("EnvironmentData/Heater.txt"), defaultData.getBytes());
    	        }

    	        catch (IOException e) {
    	            System.err.format("IOException: %s%n", e);
    	        }       		
        	}
        	
        	// Reads values from file
        	try {  
        		heaterData = new String(Files.readAllBytes(Paths.get("EnvironmentData/Heater.txt")));
            } 
            
            catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        	
        	// Sends response to GET request
        	JSONObject json = new JSONObject().put("HeaterStatus",heaterData);
            exchange.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }
	}

}
