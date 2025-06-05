package core;

import core.commands.CommandManager;
import core.hints.RandomHintProvider;
import rooms.Room;
import rooms.TaskRoom;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Room currentRoom = DataSeeder.generateRooms(this);
    private Player player = DataSeeder.getPlayer(this.currentRoom);
    private CommandManager commandManager = new CommandManager(this);
    private RandomHintProvider hintProvider = new RandomHintProvider();
    private final InputStream in;
    private boolean running;
    private Menu menu = new Menu(this);

    public Game(InputStream in) {
        this.in = in;
        this.commandManager.massRegisterCommand(DataSeeder.getCommands());
    }

    public void start() {
        RoomStatus.IN_MAIN_MENU.activate();
        menu.mainMenu();
        final Scanner scan = new Scanner(this.in);
        this.running = true;
        while (running) {
            processInput(scan.nextLine().trim());
        }
    }
    public void stop() {
        this.running = false;
    }

    private void processInput(String input) {
        if (input.isEmpty() || input.equals(null)) return;
        if (input.startsWith("/")) {handleCommand(input.substring(1)); return;}

        RoomStatus status = RoomStatus.getActiveStatus();
        if (status.equals(null)) throw new AssertionError("How did you get here?");

        switch (status) {
            case SELECTING_ROOM -> swapRoom(input);
            case IN_TASK, IN_HINT -> answerQuestion(input);
            case IN_OPTION -> menuOptions(input);
            case IN_MAIN_MENU -> mainMenuOptions(input);
            default -> throw new IllegalStateException("Invalid room status");
        }
    }

    private void handleCommand(String input) {
        this.commandManager.executeCommand(input);
    }

    private void menuOptions(String input) {
        switch (input) {
            case "1" -> { try { menu.saving(player); } catch (IOException e) { throw new RuntimeException(e); } }
            case "2" -> menu.mainMenu();
            case "3" -> RoomStatus.getPreviousStatus().activate();
            default -> System.out.println("please type one of the numbers");
        }
    }

    private void mainMenuOptions(String input) {
        switch (input) {
            case "1" -> menu.loadFromSave();
            case "2" -> menu.startNewSave();
            case "3" -> stop();
            default -> System.out.println("please type one of the numbers");
        }
    }

    private void swapRoom(String input) {
        String direction = null;
        Map<String, Room> neighboringRooms = this.currentRoom.getNeighboringRooms();
        for (String key : neighboringRooms.keySet()) if (input.equalsIgnoreCase(key)) {
            direction = key;
            break;
        }

        if (direction == null) {
            System.out.println("Invalid direction try again");
        } else {
            this.currentRoom = neighboringRooms.get(direction);
            this.currentRoom.enter();
        }
    }

    private void answerQuestion(String input) {
        if (this.currentRoom instanceof TaskRoom taskRoom) taskRoom.getTaskHandler().consume(input);
        else throw new AssertionError("How did you get here?");
    }

    public Player getPlayer() {
        return this.player;
    }
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}