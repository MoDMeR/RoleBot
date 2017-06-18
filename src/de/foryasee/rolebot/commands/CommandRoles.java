package de.foryasee.rolebot.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import de.foryasee.rolebot.command.Command;
import de.foryasee.rolebot.core.PermissionManager;
import de.foryasee.rolebot.core.RoleManager;
import de.foryasee.rolebot.util.Info;
import de.foryasee.rolebot.util.Logger;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRoles extends Command {

	public CommandRoles(String string) {
		super(string);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent e) {
		if (new PermissionManager(e.getGuild()).isPromoted(e.getAuthor().getId())) {
			if (args.length == 0) {
				EmbedBuilder b = new EmbedBuilder();
				b.setAuthor("Self assignable roles", null, "https://cdn.discordapp.com/attachments/317693756969320448/325723995028717568/unknown.png");
				String s = "";
				try {
					Scanner scr = new Scanner(new File(Info.getMainFilePath() + "/" + e.getGuild().getId(), "roles.txt"));
					while(scr.hasNextLine()) {
						s += "-" + e.getGuild().getRoleById(scr.nextLine()).getName() + "\n";
					}
					b.addField("", s, false);
					e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(10, TimeUnit.SECONDS));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			} else if (args.length >= 2) {
				if (args[0].equalsIgnoreCase("add")) {
					Role role = null;
					for (Role r : e.getGuild().getRoles()) {
						if (r.getName().equalsIgnoreCase(args[1])) {
							role = r;
						}
					}

					if (role == null) {
						EmbedBuilder b = new EmbedBuilder();
						b.setAuthor("Error", null,
								"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
						b.addField("", "Role not exists!", false);
						e.getTextChannel().sendMessage(b.build())
								.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
						return;
					}

					if (!new RoleManager(e.getGuild()).isAdded(role.getId())) {
						new RoleManager(e.getGuild()).addRole(role.getId());
						EmbedBuilder b = new EmbedBuilder();
						b.setAuthor("Role added", null,
								"https://cdn.discordapp.com/attachments/317693756969320448/325669170064785408/permission.png");
						b.addField("", role.getName() + " is now self assignable", false);
						e.getTextChannel().sendMessage(b.build())
								.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
						Logger.info("Role " + role.getId() + " is now self assignable");
					} else {
						EmbedBuilder b = new EmbedBuilder();
						b.setAuthor("Error", null,
								"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
						b.addField("Role is already added!", "", false);
						e.getTextChannel().sendMessage(b.build())
								.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					Role role = null;
					for (Role r : e.getGuild().getRoles()) {
						if (r.getName().equalsIgnoreCase(args[1])) {
							role = r;
						}
					}

					if (role == null) {
						EmbedBuilder b = new EmbedBuilder();
						b.setAuthor("Error", null,
								"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
						b.addField("", "Role not exists!", false);
						e.getTextChannel().sendMessage(b.build())
								.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
						return;
					}

					if (new RoleManager(e.getGuild()).isAdded(role.getId())) {
						new RoleManager(e.getGuild()).removeRole(role.getId());
						EmbedBuilder b = new EmbedBuilder();
						b.setAuthor("Role removed", null,
								"https://cdn.discordapp.com/attachments/317693756969320448/325669170064785408/permission.png");
						b.addField("", role.getName() + " was removed from self assignable roles", false);
						e.getTextChannel().sendMessage(b.build())
								.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
						Logger.info("Role " + role.getId() + " was removed from self assignable roles");
					} else {
						EmbedBuilder b = new EmbedBuilder();
						b.setAuthor("Error", null,
								"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
						b.addField("Role is not self assignable!", "", false);
						e.getTextChannel().sendMessage(b.build())
								.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
					}
				} else {
					EmbedBuilder b = new EmbedBuilder();
					b.setAuthor("Error", null,
							"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
					b.addField("", "Wrong command!", false);
					e.getTextChannel().sendMessage(b.build())
							.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
				}

			} else {
				EmbedBuilder b = new EmbedBuilder();
				b.setAuthor("Error", null,
						"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
				b.addField("", "Wrong command!", false);
				e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
			}
		} else {
			noPermission(e);
		}
	}

}
