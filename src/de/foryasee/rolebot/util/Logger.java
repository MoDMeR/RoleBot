package de.foryasee.rolebot.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import net.dv8tion.jda.core.entities.Guild;


public class Logger {
	
	private static File logFile;
	private static String loggerText = "";

	
	public static void startFileLogging() {
		String date = new SimpleDateFormat("dd_MM_yyyy-HH:mm:ss").format(new Date());
		String fileName = date.replace(":", "_") + ".log";
		
		new File(Info.getMainFilePath() + "/logs").mkdirs();
		logFile = new File(Info.getMainFilePath() + "/logs", fileName);
		if(!logFile.exists())
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		StringBuilder logHeader = new StringBuilder();
		logHeader.append("---- " + Info.getBotName() + " " + Info.getBotVersion() + " Log ----\n");
		logHeader.append("\n");
		logHeader.append("Time: " + date + "\n");
		logHeader.append("\n");
		logHeader.append("-- System Details --\n");
		logHeader.append("Operating System: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version") + "\n");
		logHeader.append("Java Version: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor") + "\n");
		logHeader.append("Java VM Version: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor") + "\n");
		logHeader.append("Memory: " + getMemoryText() + "\n");
		logHeader.append("\n");
		logHeader.append("-- Log --\n");
		addLogEntry(logHeader.toString());
	}
	
	private static void addLogEntry(String text) {
		try {
			//Read old logfile and add new text
			Scanner s = new Scanner(logFile);
			StringBuilder b = new StringBuilder();
			while(s.hasNextLine())
				b.append(s.nextLine() + "\n");
			text = b.toString() + text;
			//Write to logfile
			FileWriter writer = new FileWriter(logFile);
			writer.write(text);
			writer.flush();
			writer.close();
			s.close();
			loggerText = text;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void info(String text) {
		log(text, LoggerLevel.INFO);
	}

	public static void debug(String text) {
		log(text, LoggerLevel.DEBUG);
	}

	public static void error(String text) {
		log(text, LoggerLevel.ERROR);
	}
	
	public static void error(Throwable t) {
		log(t.getMessage(), LoggerLevel.ERROR);
	}

	public static void log(String text, LoggerLevel level) {
		switch (level.toString().toLowerCase()) {
		case "info":
			System.out.println("[Info] (" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ") | " + text + "");
			addLogEntry("[Info] (" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ") | " + text + "\n");
			break;
		case "debug":
			System.out.println("[Debug] (" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ") | " + text + "");
			addLogEntry("[Debug] (" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ") | " + text + "\n");
			break;
		case "error":
			System.err.println("[Error] (" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ") | " + text + "");
			addLogEntry("[Error] (" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ") | " + text + "\n");
			break;
		default:
			break;
		}
	}
	
	private static String getMemoryText() {
		Runtime var1 = Runtime.getRuntime();
        long var2 = var1.maxMemory();
        long var4 = var1.totalMemory();
        long var6 = var1.freeMemory();
        long var8 = var2 / 1024L / 1024L;
        long var10 = var4 / 1024L / 1024L;
        long var12 = var6 / 1024L / 1024L;
        
        return var6 + " bytes (" + var12 + " MB) / " + var4 + " bytes (" + var10 + " MB) up to " + var2 + " bytes (" + var8 + " MB)";
	}
	
	public static String getLoggerText() {
		return loggerText;
	}

	public enum LoggerLevel {
		INFO, DEBUG, ERROR
	}

}
