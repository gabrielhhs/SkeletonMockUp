package core.commands;

import core.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private Map<String, Command> commandList = new HashMap<>();
    private Game parent;

    public CommandManager(Game parent) {
        this.parent = parent;
    }

    public void consume(String input) {
        if (this.commandList.containsKey(input.toLowerCase())) executeCommand(this.commandList.get(input.toLowerCase()));
        else System.out.println("Invalid Command");
    }

    public void registerCommand(String keyWord, Command command) {
        if (this.commandList.containsKey(keyWord)) System.out.println("Command '" + keyWord + "' is taken select other keyword");
        else this.commandList.put(keyWord, command);
    }
    public void massRegisterCommand(Map<String, Command> commandList) {
        for (String key : commandList.keySet()) {
            if (this.commandList.containsKey(key)) System.out.println("Command '" + key + "' is taken select other keyword");
            else registerCommand(key, commandList.get(key));
        }
    }

    public void removeCommand(String keyWord) {
        this.commandList.remove(keyWord);
    }
    public void massRemoveCommand(List<String> commands) {
        for (String command : commands) removeCommand(command);
    }

    private void executeCommand(Command command) {
        command.runCommand(this);
    }

    public Game getParent() {
        return this.parent;
    }
}