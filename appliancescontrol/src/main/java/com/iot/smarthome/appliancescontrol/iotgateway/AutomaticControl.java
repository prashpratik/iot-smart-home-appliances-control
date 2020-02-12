package com.iot.smarthome.appliancescontrol.iotgateway;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This is a class for Automatic 
 * Control Mode for appliances
 */
@Component
@EnableScheduling
public class AutomaticControl {
	
	@Autowired
	private DataService dataService;
	
	/**
     * This method set appliances to be automated
     * @throws Exception in case of invalid value 
     */
	@Scheduled(fixedRate = 3000, initialDelay = 2000)
	public void AutomateAppliances() throws Exception
	{
		if(dataService.automateHeater)
		{
			AutomateHeater();
		}
		
		if(dataService.automateBlinds)
		{
			AutomateBlinds();
		}
		
		if(dataService.automateLamp)
		{
			AutomateLamp();
		}
	}
	
	/**
     * This method automate Heater
     * @throws Exception in case of invalid value 
     */
	public void AutomateHeater() throws Exception
	{
		JSONObject jsonHeater = new JSONObject();
		JSONObject jsonTemperature = new JSONObject();		
		
		if(dataService.getAllData().getInt("Temperature") <= 10 && dataService.getAllData().getInt("Location") <= 50)
		{
			jsonHeater.put("HeaterStatus", "ON");
			dataService.setHeaterStatus(jsonHeater.toString());
			
			jsonTemperature.put("Temperature", 0);
			dataService.setTemperature(jsonTemperature.toString());
		}
		else
		{
			jsonHeater.put("HeaterStatus", "OFF");
			dataService.setHeaterStatus(jsonHeater.toString());
			
			jsonTemperature.put("Temperature", 0);
			dataService.setTemperature(jsonTemperature.toString());
		}
		
	}
	
	/**
     * This method automate Blinds
     * @throws Exception in case of invalid value 
     */
	public void AutomateBlinds() throws Exception
	{
		JSONObject jsonBlinds = new JSONObject();	
		
		if(dataService.getAllData().getInt("Luminosity") > 50 && dataService.getAllData().getInt("Location") <= 50)
		{
			jsonBlinds.put("BlindsStatus", "UP");
			dataService.setBlindsStatus(jsonBlinds.toString());
		}
		else
		{
			jsonBlinds.put("BlindsStatus", "DOWN");
			dataService.setBlindsStatus(jsonBlinds.toString());
		}
		
	}
	
	/**
     * This method automate Lamp
     * @throws Exception in case of invalid value 
     */
	public void AutomateLamp() throws Exception
	{
		JSONObject jsonLamp = new JSONObject();	
		
		if(dataService.getAllData().getInt("Luminosity") <= 50 && dataService.getAllData().getInt("Location") <= 50)
		{
			jsonLamp.put("LampStatus", "ON");
			dataService.setLampStatus(jsonLamp.toString());
		}
		else
		{
			jsonLamp.put("LampStatus", "OFF");
			dataService.setLampStatus(jsonLamp.toString());
		}
		
	}

}
