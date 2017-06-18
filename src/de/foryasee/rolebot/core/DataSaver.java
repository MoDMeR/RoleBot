package de.foryasee.rolebot.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;

import de.foryasee.rolebot.util.Info;

public class DataSaver {

	private static File dataFile;
	
	public DataSaver() {
		new File(Info.getMainFilePath()).mkdirs();
		dataFile = new File(Info.getMainFilePath(), "data.txt");
		if(!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	try {
	    		JSONObject obj = new JSONObject();
	        	obj.put("token", "defaultToken");
	        	obj.put("prefix", "defaultPrefix");
	        	
				FileWriter writer = new FileWriter(dataFile);
				writer.write(obj.toString());
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveToken(String token) {
		JSONObject obj = new JSONObject();
    	obj.put("token", token);
    	obj.put("prefix", loadPrefix());
    	
    	try {
			FileWriter writer = new FileWriter(dataFile);
			writer.write(obj.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void savePrefix(String prefix) {
		JSONObject obj = new JSONObject();
    	obj.put("token", loadToken());
    	obj.put("prefix", prefix);
    	
    	try {
			FileWriter writer = new FileWriter(dataFile);
			writer.write(obj.toString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String loadToken() {
		try {
			Scanner scr = new Scanner(dataFile);
			StringBuilder sb = new StringBuilder();
			
			while(scr.hasNextLine())
				sb.append(scr.nextLine());
			
			JSONObject obj = new JSONObject(sb.toString());
			return obj.getString("token");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String loadPrefix() {
		try {
			Scanner scr = new Scanner(dataFile);
			StringBuilder sb = new StringBuilder();
			
			while(scr.hasNextLine())
				sb.append(scr.nextLine());
			
			JSONObject obj = new JSONObject(sb.toString());
			return obj.getString("prefix");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
