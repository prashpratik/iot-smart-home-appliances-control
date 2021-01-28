package com.iot.smarthome.appliancescontrol.iotgateway;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This is a class for storing all 
 * data from all sensors
 */
@Component
@EnableScheduling
public class StoreData {
	
	@Autowired
	private DataService dataService;
	
	/**
     * This method stores all data from all Sensors at some 
     * time intervals but with limited past values
     * @throws Exception in case of invalid value 
     */
	@Scheduled(fixedRate = 3000, initialDelay = 2000)
	public void storeAllData() throws Exception
	{	
		// Number of past values to store
		int dataToStore = 5;
		
		Path filePath = Paths.get("LocalStorage.json");
		
		// Checks if file exists and if not then, creates a file with default values
		if(!Files.exists(filePath))
		{
			String defaultData = "[]";
			Files.write(filePath, defaultData.getBytes());
		}
		
		String jsonArrayString = new String(Files.readAllBytes(filePath));
		JSONArray jsonArray = new JSONArray(jsonArrayString);		
		JSONObject jsonData = dataService.getAllData();
		
		System.out.print(jsonData.get("Timestamp") + " : ");
		System.out.print("Temperature:" + jsonData.get("Temperature") + ", ");
		System.out.print("HeaterStatus:" + jsonData.get("HeaterStatus") + ", ");
		System.out.print("Luminosity:" + jsonData.get("Luminosity") + ", ");
		System.out.print("BlindsStatus:" + jsonData.get("BlindsStatus") + ", ");
		System.out.print("LampStatus:" + jsonData.get("LampStatus") + ", ");
		System.out.print("Location:" + jsonData.get("Location") + ", ");
		System.out.print("RobotPosition:" + jsonData.get("RobotPosition") + ", ");
		System.out.print("RobotStatus:" + jsonData.get("RobotStatus") + ", ");
		System.out.print("AutomaticHeaterMode:" + jsonData.get("AutomaticHeaterMode") + ", ");
		System.out.print("AutomaticBlindsMode:" + jsonData.get("AutomaticBlindsMode") + ", ");
		System.out.println("AutomaticLampMode:" + jsonData.get("AutomaticLampMode"));		

		// Stores limited number of past values
		if(jsonArray.length() < dataToStore)
		{
			jsonArray.put(jsonData);
		}
		else
		{
			int arrayLength = jsonArray.length();
			for(int i=0; i<=arrayLength-dataToStore; i++)
			{
				jsonArray.remove(i);
			}			
			jsonArray.put(jsonData);
		}

		// Writes values into file
		Files.write(Paths.get("LocalStorage.json"), jsonArray.toString().getBytes());	
		
	}

}
