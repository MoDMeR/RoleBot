package de.foryasee.rolebot.commands;

import java.util.concurrent.TimeUnit;

import de.foryasee.rolebot.command.Command;
import de.foryasee.rolebot.core.RoleManager;
import de.foryasee.rolebot.core.UserRoleManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandRemove extends Command {

	public CommandRemove(String command) {
		super(command);
	}

	@Override
	public void action(String[] args, MessageReceivedEvent e) {
		if (args.length >= 1) {
			Role role = null;
			for (Role r : e.getGuild().getRoles()) {
				if (r.getName().equalsIgnoreCase(args[0])) {
					role = r;
				}
			}
			if (role == null) {
				EmbedBuilder b = new EmbedBuilder();
				b.setAuthor("Error", null,
						"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
				b.addField("", "Role not exists!", false);
				e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
				return;
			}

			if (new RoleManager(e.getGuild()).isAdded(role.getId())) {
				System.out.println(role.getName());
				if (new UserRoleManager(e).hasRole(role)) {
					new UserRoleManager(e).removeRole(role);
					EmbedBuilder b = new EmbedBuilder();
					b.setAuthor("Role removed", null,
							"https://cdn.discordapp.com/attachments/317693756969320448/325723995028717568/unknown.png");
					b.addField("", "You have removed the role " + role.getName(), false);
					e.getTextChannel().sendMessage(b.build())
							.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
				} else {
					EmbedBuilder b = new EmbedBuilder();
					b.setAuthor("Error", null,
							"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
					b.addField("", "You don't have this role!", false);
					e.getTextChannel().sendMessage(b.build())
							.queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
				}
			} else {
				EmbedBuilder b = new EmbedBuilder();
				b.setAuthor("Error", null,
						"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
				b.addField("", "Role is not self assignable!", false);
				e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
			}
		} else {
			EmbedBuilder b = new EmbedBuilder();
			b.setAuthor("Error", null,
					"https://cdn.discordapp.com/attachments/317693756969320448/325662383894953984/unknown.png");
			b.addField("", "This command needs two arguments!", false);
			e.getTextChannel().sendMessage(b.build()).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
		}
	}
}
