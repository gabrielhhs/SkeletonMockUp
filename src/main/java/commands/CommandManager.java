package commands;

import core.Game;
import core.Registry;

public class CommandManager {
    private final Game parent;
    private final Registry<Command> commandRegistry;

    public CommandManager(Game parent, Registry<Command> registry) {
        this.parent = parent;
        this.commandRegistry = registry;
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
        this.commandRegistry.get(commandName).ifPresentOrElse(
            command -> this.executeCommand(command, args),
            () -> System.out.println("Invalid command")
        );
    }

    public Game getParent() {
        return this.parent;
    }
}