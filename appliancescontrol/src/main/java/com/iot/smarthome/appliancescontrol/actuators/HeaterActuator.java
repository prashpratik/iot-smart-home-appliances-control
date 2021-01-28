package com.iot.smarthome.appliancescontrol.actuators;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * This is a class for creating Heater Actuator and
 * implementing a CoAP Server for it
 */
@Component
public class HeaterActuator {

	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public HeaterActuator() {
		
		System.out.println("========================================");
		System.out.println("Heater Actuator Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5693);

		server.add(new SetHeaterStatus(), new SetTemperature());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Heater Actuator Started");
		System.out.println("========================================");
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to set Heater Status
	 */
	public static class SetHeaterStatus extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public SetHeaterStatus() {
        	
            super("setHeaterStatus");
            
            getAttributes().setTitle("Set Heater Status");
        }

        /**
         * This method handles the POST request for Heater Status and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handlePOST(CoapExchange exchange) {
        	
        	// Sets Heater Status from POST request
        	try {
        		String data = "";
    			JSONObject json = new JSONObject(exchange.getRequestText());
    			data = json.getString("HeaterStatus");
    			
    			// Checks if file exists and if not then, creates a file
    			if(!Files.exists(Paths.get("EnvironmentData/Heater.txt")))
            	{    				
    				if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}            		       		
            	}
    			
    			// Writes values into file
    			Files.write(Paths.get("EnvironmentData/Heater.txt"), data.getBytes());
            	
    			// Sends response to POST request
            	JSONObject jsonResponse = new JSONObject().put("message","POST_REQUEST_SUCCESS");        	
            	exchange.respond(ResponseCode.CONTENT, jsonResponse.toString(), MediaTypeRegistry.APPLICATION_JSON);
	        }

	        catch (Exception e) {
	            System.err.format("Exception: %s%n", e);
	        }
        	
		}
        
    }
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to set Temperature
	 */
	public static class SetTemperature extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public SetTemperature() {
        	
            super("setTemperature");
            
            getAttributes().setTitle("Set Temperature");
        }
        
        // Initializing counter for the number of hits and previous data
        int count = 0;
        int previousData = 0;

        /**
         * This method handles the POST request for Temperature and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handlePOST(CoapExchange exchange) {     	
        	
        	// Sets Temperature from POST request			
        	// Creates the random data and saves it for some number of hits
        	try {
        		int data = 0;
    			JSONObject json = new JSONObject(exchange.getRequestText());
    			int temperature = json.getInt("Temperature");			
    			
    			if(temperature == 0)
    			{	
    				count++;
    				
    				if(count == 1)
    				{
    					data = (int)(Math.random()*9 + 5);
    					previousData = data;
    				}
    				else if (count > 1 && count < 4)
    				{
    					data = previousData;
    				}
    				else 
    				{
    					data = previousData;
    					count = 0;
    				}
    			}
    			else 
    			{
    				temperature = Math.round(temperature/10) + 10;					
    				data = temperature;
    			}
    			
    			// Checks if file exists and if not then, creates a file
    			if(!Files.exists(Paths.get("EnvironmentData/Temperature.txt")))
            	{
    				if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}
            	}
    			
    			// Writes values into file
    			Files.write(Paths.get("EnvironmentData/Temperature.txt"), Integer.toString(data).getBytes());	
            	
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
