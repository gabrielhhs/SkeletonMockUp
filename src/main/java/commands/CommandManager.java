package commands;

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

    private void executeCommand(Command command, String args) {
        command.run(this, args);
    }
    public void executeCommand(String input) {
        int spaceIndex = input.indexOf(' ');
        if (spaceIndex != -1) {
            String commandName = input.substring(0, spaceIndex);
            String args = input.substring(spaceIndex + 1);
            this.executeCommand(commandName, args);
        } else {
            this.executeCommand(input, null);
        }
    }
    public void executeCommand(String commandName, String args) {
        if (this.commandList.containsKey(commandName)) this.executeCommand(this.commandList.get(commandName), args);
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