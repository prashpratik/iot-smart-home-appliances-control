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
 * This is a class for creating Robot Position Sensor and
 * implementing a CoAP Server for it
 */
@Component
public class RobotPositionSensor {
	
	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public RobotPositionSensor()
	{
		System.out.println("========================================");
		System.out.println("Robot Position Sensor Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5691);

		server.add(new GetRobotPosition());       

		server.start();
		
		System.out.println("----------------------------------------");
		System.out.println("Robot Position Sensor Started");
		System.out.println("========================================");
	}
	
	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to get Robot Position
	 */
	public static class GetRobotPosition extends CoapResource {
		
		/**
         * This constructor creates a CoAP Resource
         */
        public GetRobotPosition() {
        	
            super("getRobotPosition");
            
            getAttributes().setTitle("Get Robot Position");
        }

        /**
         * This method handles the GET request for Robot Position and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handleGET(CoapExchange exchange) { 
        	
        	String robotPosition="";
        	
        	// Checks if file exists and if not then, creates a file with default values
        	if(!Files.exists(Paths.get("EnvironmentData/RobotPosition.txt")))
        	{
        		try {
        			if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}
        			String defaultData = "0,0";
        			Files.write(Paths.get("EnvironmentData/RobotPosition.txt"), defaultData.getBytes());
    	        }

    	        catch (IOException e) {
    	            System.err.format("IOException: %s%n", e);
    	        }       		
        	}

        	// Reads values from file
        	try {  
        		robotPosition = new String(Files.readAllBytes(Paths.get("EnvironmentData/RobotPosition.txt")));
            } 
            
            catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        	
        	// Sends response to GET request
        	JSONObject json = new JSONObject().put("RobotPosition",robotPosition);
            exchange.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }
	}

}
