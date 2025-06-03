package core.commands.commandslist;

import core.commands.Command;
import core.commands.CommandManager;

public class HealCommand implements Command {
    private String keyWord;

    public HealCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void run(CommandManager commandManager) {
        commandManager.getParent().getPlayer().heal();
    }

    @Override
    public String getKeyWord() {
        return "";
    }
}