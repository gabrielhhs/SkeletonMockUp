package core.commands;

import core.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandManager {
    private Map<String, Command> commandList = new HashMap<>();
    private Game parent;

    public CommandManager(Game parent) {
        this.parent = parent;
    }

    private void executeCommand(Command command) {
        command.run(this);
    }
    public void executeCommand(String input) {
        if (this.commandList.containsKey(input.toLowerCase())) executeCommand(this.commandList.get(input.toLowerCase()));
        else System.out.println("Invalid Command");
    }

    public void registerCommand(Command command) {
        if (this.commandList.containsKey(command.getKeyWord())) System.out.println("Command '" + command.getKeyWord() + "' is taken select other keyword");
        else this.commandList.put(command.getKeyWord(), command);
    }

    public void massRegisterCommand(Set<Command> commandList) {
        for (Command command : commandList) {
            if (this.commandList.containsKey(command.getKeyWord())) System.out.println("Command '" + command.getKeyWord() + "' is taken select other keyword");
            else registerCommand(command);
        }
    }
    public void removeCommand(String keyWord) {
        this.commandList.remove(keyWord);
    }

    public void massRemoveCommand(Set<String> commands) {
        for (String command : commands) removeCommand(command);
    }

    public Game getParent() {
        return this.parent;
    }
}