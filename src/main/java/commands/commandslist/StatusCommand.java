package commands.commandslist;

import core.Player;
import commands.Command;
import commands.CommandManager;

public class StatusCommand implements Command {
    private final String keyWord;

    public StatusCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void run(CommandManager commandManager, String args) {
        Player player = commandManager.getParent().getPlayer();
        System.out.printf("HP: %s%nScore: %s", player.getHealth(), player.getScore());
    }

    @Override
    public String getKeyWord() {
        return this.keyWord;
    }
}