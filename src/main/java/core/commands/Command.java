package core.commands;

public interface Command {
    void run(CommandManager commandManager, String args);
    String getKeyWord();
}