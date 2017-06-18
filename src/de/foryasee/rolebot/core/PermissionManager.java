package de.foryasee.rolebot.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import de.foryasee.rolebot.util.Info;
import net.dv8tion.jda.core.entities.Guild;

public class PermissionManager {

	private File permFile;

	private Guild g;

	public PermissionManager(Guild g) {
		this.g = g;
		permFile = new File(Info.getMainFilePath() + "/" + g.getId(), "permissions.txt");
		createFileIfNotExists();
	}

	public void promoteUser(String id) {
		if(!isPromoted(id)) {
			writeToFile(getFileContent() + id);
		}
	}

	public void demoteUser(String id) {
		if(isPromoted(id)) {
			writeToFile(getFileContent().replace(id + "\n", ""));
		}
	}

	public boolean isPromoted(String id) {
		try {
			Scanner scr = new Scanner(permFile);
			
			while(scr.hasNextLine())
				if(scr.nextLine().equals(id))
					return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void writeToFile(String content) {
		try {
			FileWriter writer = new FileWriter(permFile);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getFileContent() {
		try {
		Scanner s = new Scanner(permFile);
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
		if (!permFile.exists()) {
			try {
				permFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
