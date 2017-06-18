package de.foryasee.rolebot.command;

import de.foryasee.rolebot.main.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getMessage().getContent().startsWith(Main.getPrefix())
				&& e.getMessage().getAuthor().getId() != e.getJDA().getSelfUser().getId()) {
			CommandHandler.handleCommand(CommandHandler.parser.parse(e.getMessage().getContent().toLowerCase(), e));
		}
	}

}
