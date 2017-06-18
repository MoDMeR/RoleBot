package de.foryasee.rolebot.core;

import de.foryasee.rolebot.command.CommandHandler;
import de.foryasee.rolebot.commands.CommandAdd;
import de.foryasee.rolebot.commands.CommandDemote;
import de.foryasee.rolebot.commands.CommandHelp;
import de.foryasee.rolebot.commands.CommandPromo;
import de.foryasee.rolebot.commands.CommandPromote;
import de.foryasee.rolebot.commands.CommandRemove;
import de.foryasee.rolebot.commands.CommandRole;
import de.foryasee.rolebot.commands.CommandRoles;

public class CommandManager {

	public CommandManager() {
		initCommands();
	}

	private void initCommands() {
		CommandHandler.addCommand(new CommandHelp("help"));
		
		//Permission Commands
		CommandHandler.addCommand(new CommandPromote("promote"));
		CommandHandler.addCommand(new CommandDemote("demote"));
		CommandHandler.addCommand(new CommandPromo("promo"));
		
		//Role Management
		CommandHandler.addCommand(new CommandRoles("roles"));
		CommandHandler.addCommand(new CommandAdd("add"));
		CommandHandler.addCommand(new CommandRemove("remove"));
		
		//Role assign
		CommandHandler.addCommand(new CommandRole("role"));
	}
}
