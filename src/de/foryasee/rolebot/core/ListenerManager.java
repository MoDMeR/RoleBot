package de.foryasee.rolebot.core;

import de.foryasee.rolebot.command.CommandListener;
import net.dv8tion.jda.core.JDABuilder;

public class ListenerManager {

	private JDABuilder b;
	
	public ListenerManager(JDABuilder builder) {
		this.b = builder;
		initListener();
	}

	private void initListener() {
		b.addEventListener(new CommandListener());
	}
}
