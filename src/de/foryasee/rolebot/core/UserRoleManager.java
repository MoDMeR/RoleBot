package de.foryasee.rolebot.core;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

public class UserRoleManager {

	private MessageReceivedEvent e;
	
	public UserRoleManager(MessageReceivedEvent e) {
		this.e = e;
	}
	
	public void addRole(Role r) {
		if(!hasRole(r)) {
			new GuildController(e.getGuild()).addRolesToMember(e.getMember(), r).queue();
		}
	}
	
	public void removeRole(Role r) {
		if(hasRole(r)) {
			new GuildController(e.getGuild()).removeRolesFromMember(e.getMember(), r).queue();
		}
	}
	
	public boolean hasRole(Role r) {
		if(e.getMember().getRoles().contains(r)) {
			return true;
		}
		return false;
	}
}
