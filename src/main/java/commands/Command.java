package commands;

import core.Registerable;

public interface Command extends Registerable {
    void run(CommandManager commandManager, String args);
}