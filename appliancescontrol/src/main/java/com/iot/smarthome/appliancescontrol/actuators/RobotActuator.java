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
 * This is a class for creating Robot Actuator and
 * implementing a CoAP Server for it
 */
@Component
public class RobotActuator {
	
	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public RobotActuator() {
		
		System.out.println("========================================");
		System.out.println("Robot Actuator Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5696);

		server.add(new SetRobotStatus(), new SetRobotPosition());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Robot Actuator Started");
		System.out.println("========================================");
		
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to set Robot Status
	 */
	public static class SetRobotStatus extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public SetRobotStatus() {
        	
            super("setRobotStatus");
            
            getAttributes().setTitle("Set Robot Status");
        }

        /**
         * This method handles the POST request for Robot Status and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handlePOST(CoapExchange exchange) {
        	
        	// Sets Robot Status from POST request
        	try {
        		String data = "";
    			JSONObject json = new JSONObject(exchange.getRequestText());
    			data = json.getString("RobotStatus");
    			
    			// Checks if file exists and if not then, creates a file
    			if(!Files.exists(Paths.get("EnvironmentData/Robot.txt")))
            	{    				
    				if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}            		       		
            	}
    			
    			// Writes values into file
    			Files.write(Paths.get("EnvironmentData/Robot.txt"), data.getBytes());
            	
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
	 * to set Robot Position
	 */
	public static class SetRobotPosition extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public SetRobotPosition() {
        	
            super("setRobotPosition");
            
            getAttributes().setTitle("Set Robot Position");
        }

        /**
         * This method handles the POST request for Robot Position and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handlePOST(CoapExchange exchange) {
        	
        	// Sets Robot Position from POST request
        	try {
        		String data = "";
    			JSONObject json = new JSONObject(exchange.getRequestText());
    			data = json.getString("RobotPosition");
    			
    			// Checks if file exists and if not then, creates a file
    			if(!Files.exists(Paths.get("EnvironmentData/RobotPosition.txt")))
            	{    				
    				if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}            		       		
            	}
    			
    			// Writes values into file
    			Files.write(Paths.get("EnvironmentData/RobotPosition.txt"), data.getBytes());
            	
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
