package core.commands.commandslist;

import core.commands.Command;
import core.commands.CommandManager;

public class SuicideCommand implements Command {
    @Override
    public void runCommand(CommandManager commandManager) {
        commandManager.getParent().getPlayer().kill();
    }
}