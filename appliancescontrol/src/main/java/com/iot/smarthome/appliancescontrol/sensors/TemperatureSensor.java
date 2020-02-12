package com.iot.smarthome.appliancescontrol.sensors;

import java.io.IOException;
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
 * This is a class for creating Temperature Sensor and
 * implementing a CoAP Server for it
 */
@Component
public class TemperatureSensor {

	/**
	 * This constructor starts a CoAP Server and
	 * also adds CoAP Resource to it
	 */
	public TemperatureSensor() {
		
		System.out.println("========================================");
		System.out.println("Temperature Sensor Starting");
		System.out.println("----------------------------------------");
		
		CoapServer server = new CoapServer(5685);

		server.add(new GetTemperature());       

		server.start();		
		
		System.out.println("----------------------------------------");
		System.out.println("Temperature Sensor Started");
		System.out.println("========================================");
	}

	/**
	 * This is a class for implementing a CoAP Resource in order
	 * to get Temperature
	 */
	public static class GetTemperature extends CoapResource {
		
        /**
         * This constructor creates a CoAP Resource
         */
        public GetTemperature() {
        	
            super("getTemperature");
            
            getAttributes().setTitle("Get Temperature");
        }

        /**
         * This method handles the GET request for Temperature and
         * sends response for it
         * @param exchange Creates a CoapExchange for request and response 
         */
        @Override
        public void handleGET(CoapExchange exchange) { 
        	
        	int temperatureData = 0;
        	String temperatureDataString = "";
        	
        	// Checks if file exists and if not then, creates a file with default values
        	if(!Files.exists(Paths.get("EnvironmentData/Temperature.txt")))
        	{
        		try {
        			if(!Files.exists(Paths.get("EnvironmentData")))
        			{
        				Files.createDirectory(Paths.get("EnvironmentData"));
        			}
        			String defaultData = "5";
        			Files.write(Paths.get("EnvironmentData/Temperature.txt"), defaultData.getBytes());
    	        }

    	        catch (IOException e) {
    	            System.err.format("IOException: %s%n", e);
    	        }       		
        	}

        	// Reads values from file
        	try {  
        		temperatureDataString = new String(Files.readAllBytes(Paths.get("EnvironmentData/Temperature.txt")));
        		temperatureData = Integer.parseInt(temperatureDataString);
            } 
            
            catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
        	
        	// Sends response to GET request
        	JSONObject json = new JSONObject().put("Temperature",temperatureData);
            exchange.respond(ResponseCode.CONTENT, json.toString(), MediaTypeRegistry.APPLICATION_JSON);
        }
    }

}
