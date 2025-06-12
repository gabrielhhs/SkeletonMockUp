package commands;

import commands.commandslist.*;
import core.Registry;

public abstract class GameCommands {
	public static Registry<Command> register(Registry<Command> registry) {
		return registry.register(
			new StatusCommand("status"),
			new SuicideCommand("kill"),
			new InventoryCommand("inventory"),
			new UseCommand(),
			new MenuCommand("menu")
		);
	}
}