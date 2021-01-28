package com.iot.smarthome.appliancescontrol.iotgateway;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a class for Data Service where
 * we can get data from sensors and 
 * send data to actuators
 */
@Service
public class DataService {
	
	@Autowired
	private CoapInterface coapInterface;
	
	// Initializing devices to be automated
	boolean automateHeater = true;
	boolean automateBlinds = true;
	boolean automateLamp = true;

	/**
     * This method gets all data from all Sensors
     * @return JSONObject Returns data in form of JSON Object
     * @throws Exception in case of invalid value 
     */
	public JSONObject getAllData() throws Exception
	{
		JSONObject jsondata = new JSONObject();
		
		jsondata.put("Timestamp",LocalDateTime.now());		
		jsondata.put("Temperature",coapInterface.getCoapClient("coap://localhost:5685/getTemperature").get("Temperature"));
		jsondata.put("HeaterStatus",coapInterface.getCoapClient("coap://localhost:5686/getHeaterStatus").get("HeaterStatus"));
		jsondata.put("Luminosity",coapInterface.getCoapClient("coap://localhost:5687/getLuminosity").get("Luminosity"));
		jsondata.put("BlindsStatus",coapInterface.getCoapClient("coap://localhost:5688/getBlindsStatus").get("BlindsStatus"));
		jsondata.put("LampStatus",coapInterface.getCoapClient("coap://localhost:5689/getLampStatus").get("LampStatus"));
		jsondata.put("Location",coapInterface.getCoapClient("coap://localhost:5690/getLocation").get("Location"));
		jsondata.put("RobotPosition",coapInterface.getCoapClient("coap://localhost:5691/getRobotPosition").get("RobotPosition"));		
		jsondata.put("RobotStatus",coapInterface.getCoapClient("coap://localhost:5692/getRobotStatus").get("RobotStatus"));
		jsondata.put("AutomaticHeaterMode",automateHeater);
		jsondata.put("AutomaticBlindsMode",automateBlinds);
		jsondata.put("AutomaticLampMode",automateLamp);
	
		return jsondata;
	}
	
	/**
     * This method sends Heater Status 
     * to Heater Actuator
     * @param postData Data to be posted
     * @throws Exception in case of invalid value 
     */
	public void setHeaterStatus(String postData) throws Exception
	{		
		coapInterface.postCoapClient("coap://localhost:5693/setHeaterStatus", postData);
	}
	
	/**
     * This method sends Temperature 
     * to Heater Actuator
     * @param postData Data to be posted
     * @throws Exception in case of invalid value 
     */
	public void setTemperature(String postData) throws Exception
	{		
		coapInterface.postCoapClient("coap://localhost:5693/setTemperature", postData);
	}
	
	/**
     * This method sends Blinds Status 
     * to Blinds Actuator
     * @param postData Data to be posted
     * @throws Exception in case of invalid value 
     */
	public void setBlindsStatus(String postData) throws Exception
	{		
		coapInterface.postCoapClient("coap://localhost:5694/setBlindsStatus", postData);
	}
	
	/**
     * This method sends Lamp Status 
     * to Lamp Actuator
     * @param postData Data to be posted
     * @throws Exception in case of invalid value 
     */
	public void setLampStatus(String postData) throws Exception
	{		
		coapInterface.postCoapClient("coap://localhost:5695/setLampStatus", postData);
	}
	
	/**
     * This method sends Robot Status 
     * to Robot Actuator
     * @param postData Data to be posted
     * @throws Exception in case of invalid value 
     */
	public void setRobotStatus(String postData) throws Exception
	{		
		coapInterface.postCoapClient("coap://localhost:5696/setRobotStatus", postData);
	}
	
	/**
     * This method sends Robot Position 
     * to Robot Actuator
     * @param postData Data to be posted
     * @throws Exception in case of invalid value 
     */
	public void setRobotPosition(String postData) throws Exception
	{		
		coapInterface.postCoapClient("coap://localhost:5696/setRobotPosition", postData);
	}
	
}
