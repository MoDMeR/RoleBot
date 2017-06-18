package de.foryasee.rolebot.command;

import java.util.concurrent.TimeUnit;

import de.foryasee.rolebot.core.PermissionManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

public abstract class Command {

	protected String command;

	public Command(String command) {
		this.command = command;
	}

	public void call(String[] args, MessageReceivedEvent e) {
		if(e.getGuild().getOwner() == e.getGuild().getMemberById(e.getAuthor().getId()))
			new PermissionManager(e.getGuild()).promoteUser(e.getAuthor().getId());
		
		action(args, e);
		try {
			e.getMessage().delete().queue();
		} catch (ErrorResponseException ex) {

		}
	}

	public abstract void action(String[] args, MessageReceivedEvent e);

	protected void noPermission(MessageReceivedEvent e) {
		EmbedBuilder b = new EmbedBuilder();
		b.setAuthor("No permissions", null, "https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
		b.addField("", "You don't have permissions to do that!", false);
		e.getTextChannel().sendMessage(b.build()).queue(msg2 -> msg2.delete().queueAfter(5, TimeUnit.SECONDS));
	}
	
	public String getCommand() {
		return command;
	}
}
