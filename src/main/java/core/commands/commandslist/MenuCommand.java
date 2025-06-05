package core.commands.commandslist;

import core.commands.Command;
import core.commands.CommandManager;

public class MenuCommand implements Command {
    private final String keyWord;

    public MenuCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void run(CommandManager commandManager, String args) {
        commandManager.getParent().getMenu().options();
    }

    @Override
    public String getKeyWord() {
        return this.keyWord;
    }
}
