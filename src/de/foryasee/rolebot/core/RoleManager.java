package de.foryasee.rolebot.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import de.foryasee.rolebot.util.Info;
import net.dv8tion.jda.core.entities.Guild;

public class RoleManager {

	private File roleFile;

	private Guild g;

	public RoleManager(Guild g) {
		this.g = g;
		roleFile = new File(Info.getMainFilePath() + "/" + g.getId(), "roles.txt");
		createFileIfNotExists();
	}

	public void addRole(String id) {
		if(!isAdded(id)) {
			writeToFile(getFileContent() + id);
		}
	}

	public void removeRole(String id) {
		if(isAdded(id)) {
			writeToFile(getFileContent().replace(id + "\n", ""));
		}
	}

	public boolean isAdded(String id) {
		try {
			Scanner scr = new Scanner(roleFile);
			
			while(scr.hasNextLine())
				if(scr.nextLine().equals(id))
					return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getPromotedUser() {
		return null;
	}

	private void writeToFile(String content) {
		try {
			FileWriter writer = new FileWriter(roleFile);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getFileContent() {
		try {
		Scanner s = new Scanner(roleFile);
		StringBuilder sb = new StringBuilder();
		
		while(s.hasNextLine())
			sb.append(s.nextLine() + "\n");
		s.close();
		return sb.toString();
		
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private void createFileIfNotExists() {
		new File(Info.getMainFilePath() + "/" + g.getId()).mkdirs();
		if (!roleFile.exists()) {
			try {
				roleFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
