package com.iot.smarthome.appliancescontrol.iotgateway;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a class for Data Controller where
 * requests from HTTP client are handled
 */
@RestController
public class DataController {
	
	@Autowired
	private DataService dataService;
	
	/**
     * This method handles GET request for all data
     * from all sensors and sends response for it 
     * @return String Returns XML data in form of String
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(value = "/getAllData", produces = "application/xml")
	public String getAllData() throws Exception {
		
		String xmlString = "";	
		
		JSONObject json = dataService.getAllData();	
		
		// Converts JSON to XML
		xmlString = "<data>" + XML.toString( json ) + "</data>";
        
        return xmlString;		
	}
	
	/**
     * This method handles POST request for 
     * Temperature
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setTemperature", consumes = "application/xml")
	public void setTemperature(@RequestBody String xml) throws Exception {
		
		// Converts XML to JSON
		JSONObject jsonTemperature = XML.toJSONObject(xml);		
		dataService.setTemperature(jsonTemperature.toString());
		
		JSONObject jsonHeater = new JSONObject();		
		if (jsonTemperature.getInt("Temperature") == 0)
		{			
			jsonHeater.put("HeaterStatus", "OFF");
			dataService.setHeaterStatus(jsonHeater.toString());
		}
		else
		{
			jsonHeater.put("HeaterStatus", "ON");
			dataService.setHeaterStatus(jsonHeater.toString());
		}
		
	}	
	
	/**
     * This method handles POST request for
     * Automate Heater
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setAutoTemperature", consumes = "application/xml")
	public void setAutoTemperature(@RequestBody String xml) throws Exception {
		
		// Converts XML to JSON
		JSONObject jsonAutoTemperature = XML.toJSONObject(xml);
		dataService.automateHeater = jsonAutoTemperature.getBoolean("AutomateHeater");
		
	}
	
	/**
     * This method handles POST request for
     * Blinds Status
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setBlindsStatus", consumes = "application/xml")
	public void setBlindsStatus(@RequestBody String xml) throws Exception {
		
		// Converts XML to JSON
		JSONObject jsonBlindsStatus = XML.toJSONObject(xml);		
		dataService.setBlindsStatus(jsonBlindsStatus.toString());
		
	}
	
	/**
     * This method handles POST request for
     * Automate Blinds
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setAutoBlinds", consumes = "application/xml")
	public void setAutoBlinds(@RequestBody String xml) throws Exception {
		
		// Converts XML to JSON
		JSONObject jsonAutoBlinds = XML.toJSONObject(xml);
		dataService.automateBlinds = jsonAutoBlinds.getBoolean("AutomateBlinds");
		
	}
	
	/**
     * This method handles POST request for
     * Lamp Status 
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setLampStatus", consumes = "application/xml")
	public void setLampStatus(@RequestBody String xml) throws Exception {
		
		// Converts XML to JSON
		JSONObject jsonLampStatus = XML.toJSONObject(xml);		
		dataService.setLampStatus(jsonLampStatus.toString());
		
	}
	
	/**
     * This method handles POST request for
     * Automate Lamp
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setAutoLamp", consumes = "application/xml")
	public void setAutoLamp(@RequestBody String xml) throws Exception {
		
		//Converts XML to JSON
		JSONObject jsonAutoLamp = XML.toJSONObject(xml);
		dataService.automateLamp = jsonAutoLamp.getBoolean("AutomateLamp");
		
	}
	
	/**
     * This method handles POST request for
     * Robot Status
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setRobotStatus", consumes = "application/xml")
	public void setRobotStatus(@RequestBody String xml) throws Exception {
		
		//Converts XML to JSON
		JSONObject jsonRobotStatus = XML.toJSONObject(xml);		
		dataService.setRobotStatus(jsonRobotStatus.toString());
		
	}
	
	/**
     * This method handles POST request for
     * Robot Position
     * @param xml XML Data to be posted
     * @throws Exception in case of invalid value 
     */
	@RequestMapping(method = RequestMethod.POST, value = "/setRobotDirection", consumes = "application/xml")
	public void setRobotPosition(@RequestBody String xml) throws Exception {
		
		JSONObject jsonRobotPosition = new JSONObject();
		
		String robotStatus = dataService.getAllData().getString("RobotStatus");
		
		String coordinates = dataService.getAllData().getString("RobotPosition");
		int x = Integer.parseInt(coordinates.split(",")[0]);
		int y = Integer.parseInt(coordinates.split(",")[1]);		

		//Converts XML to JSON
		JSONObject jsonRobotDirection = XML.toJSONObject(xml);
		
		if(robotStatus.equals("ON")) {
			
			if(jsonRobotDirection.getString("RobotDirection").equals("left") && x!=0) {
				x = x-10;
			}
			else if(jsonRobotDirection.getString("RobotDirection").equals("right") && x!=160) {
				x = x+10;
			}
			else if(jsonRobotDirection.getString("RobotDirection").equals("forward") && y!=0) {
				y = y-10;
			}
			else if(jsonRobotDirection.getString("RobotDirection").equals("backward") && y!=70) {
				y = y+10;
			}
		}		
		
		jsonRobotPosition.put("RobotPosition", x + "," + y);
		dataService.setRobotPosition(jsonRobotPosition.toString());
		
	}
}
