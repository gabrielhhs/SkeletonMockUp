package core.commands.commandslist;

import core.commands.Command;
import core.commands.CommandManager;

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
    public String getKeyWord() {
        return this.keyWord;
    }
}