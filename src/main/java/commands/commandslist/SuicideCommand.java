package commands.commandslist;

import commands.Command;
import commands.CommandManager;

public class SuicideCommand implements Command {
    private final String keyWord;

    public SuicideCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void run(CommandManager commandManager, String args) {
        commandManager.getParent().getPlayer().kill();
    }

    @Override
    public String getId() {
        return this.keyWord;
    }
}