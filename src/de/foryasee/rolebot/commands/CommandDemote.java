package de.foryasee.rolebot.commands;

import java.util.concurrent.TimeUnit;

import de.foryasee.rolebot.command.Command;
import de.foryasee.rolebot.core.PermissionManager;
import de.foryasee.rolebot.util.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandDemote extends Command {

	public CommandDemote(String string) {
		super(string);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent e) {
		if(e.getGuild().getOwner() == e.getGuild().getMemberById(e.getAuthor().getId())) {
			if(args.length >= 1) {
				String id = e.getMessage().getMentionedUsers().get(0).getId();
				if(new PermissionManager(e.getGuild()).isPromoted(id)) {
					new PermissionManager(e.getGuild()).demoteUser(id);
					EmbedBuilder b = new EmbedBuilder();
					b.setAuthor("User demoted", null, "https://cdn.discordapp.com/attachments/317693756969320448/325669170064785408/permission.png");
					b.addField("", e.getMessage().getMentionedUsers().get(0).getName() + " is now demoted", false);
					e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
					Logger.info("User " + id + " is now demoted");
				} else {
					EmbedBuilder b = new EmbedBuilder();
					b.setAuthor("Error", null, "https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
					b.addField("User is already demoted!", "", false);
					e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
				}
			} else {
				EmbedBuilder b = new EmbedBuilder();
				b.setAuthor("Error", null, "https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
				b.addField("", "This command needs one argument!", false);
				e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
			}
		} else {
			noPermission(e);
		}
	}

}
