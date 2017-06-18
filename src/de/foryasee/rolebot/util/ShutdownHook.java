package de.foryasee.rolebot.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShutdownHook {

	public ShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				Logger.info("Stopping bot...");
				createLatestLog();
			}
		}));
	}
	
	private void createLatestLog() {
		File file = new File(Info.getMainFilePath() + "/logs" + "latest.log");
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(Logger.getLoggerText());
			writer.flush();
			writer.close();
			Logger.info("Log created!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
