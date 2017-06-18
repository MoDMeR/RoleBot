package de.foryasee.rolebot.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import de.foryasee.rolebot.command.Command;
import de.foryasee.rolebot.core.PermissionManager;
import de.foryasee.rolebot.util.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandPromo extends Command {

	public CommandPromo(String command) {
		super(command);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent e) {
		if (new PermissionManager(e.getGuild()).isPromoted(e.getAuthor().getId())) {
			if (args.length == 0) {
				EmbedBuilder b = new EmbedBuilder();
				b.setAuthor("Promoted Users", null,
						"https://cdn.discordapp.com/attachments/317693756969320448/325723995028717568/unknown.png");
				String s = "";
				try {
					Scanner scr = new Scanner(
							new File(Info.getMainFilePath() + "/" + e.getGuild().getId(), "permissions.txt"));
					while (scr.hasNextLine()) {
						s += "-" + e.getGuild().getMemberById(scr.nextLine()).getEffectiveName() + "\n";
					}
					b.addField("", s, false);
					e.getTextChannel().sendMessage(b.build())
							.queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				EmbedBuilder b = new EmbedBuilder();
				b.setAuthor("Error", null,
						"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
				b.addField("", "This command needs no arguments!", false);
				e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
			}
		} else {
			noPermission(e);
		}
	}

}
