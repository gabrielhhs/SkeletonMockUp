package core;

import commands.CommandManager;
import rooms.Room;
import rooms.SpecialEventRoom;
import rooms.TaskRoom;
import rooms.TaskRoomWithEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private final Room initialRoom = DataSeeder.generateRooms(this);
    private Player player = DataSeeder.getPlayer(this.initialRoom);
    private CommandManager commandManager = new CommandManager(this);
    private final InputStream in;
    private boolean running;
    private Menu menu = new Menu(this);
    private final StatusManager status = new StatusManager();

    public Game(InputStream in) {
        this.in = in;
        this.commandManager.massRegisterCommand(DataSeeder.getCommands());
    }

    public void start() {
        this.status.set(GameStatus.IN_MAIN_MENU);
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

        GameStatus status = this.status.get();
        if (status.equals(null)) throw new AssertionError("How did you get here?");

        switch (status) {
            case SELECTING_ROOM -> this.swapRoom(input);
            case IN_TASK, IN_HINT -> this.answerQuestion(input);
            case IN_OPTION -> this.menuOptions(input);
            case IN_MAIN_MENU -> this.mainMenuOptions(input);
            case IN_EVENT -> this.sendInputToEvent(input);
            default -> throw new IllegalStateException("Invalid room status");
        }
    }

    private void handleCommand(String input) {
        this.commandManager.executeCommand(input);
    }

    //ToDo: move logic to respective class
    private void menuOptions(String input) {
        switch (input) {
            case "1" -> { try { menu.saving(player); } catch (IOException e) { throw new RuntimeException(e); } }
            case "2" -> menu.mainMenu();
            case "3" -> this.status.revert();
            default -> System.out.println("please type one of the numbers");
        }
    }

    //ToDo: move logic to respective class
    private void mainMenuOptions(String input) {
        switch (input) {
            case "1" -> menu.loadFromSave();
            case "2" -> menu.startNewSave();
            case "3" -> this.stop();
            default -> System.out.println("please type one of the numbers");
        }
    }

    public Room getInitialRoom() {
        return this.initialRoom;
    }

    //ToDo: move logic to respective class
    private void swapRoom(String input) {
        String direction = null;
        Map<String, Room> neighboringRooms = player.getCurrentRoom().getNeighboringRooms();
        for (String key : neighboringRooms.keySet()) if (input.equalsIgnoreCase(key)) {
            direction = key;
            break;
        }

        if (direction == null) {
            System.out.println("Invalid direction try again");
        } else {
            this.player.setCurrentRoom(neighboringRooms.get(direction));
            this.player.getCurrentRoom().enter();
        }
    }

    private void sendInputToEvent(String input) {
        if (this.player.getCurrentRoom() instanceof TaskRoomWithEvent room) room.getEventHandler().consume(input);
        else if (this.player.getCurrentRoom() instanceof SpecialEventRoom room) room.getEventHandler().consume(input);
        else System.out.println("How did you get here?");
    }

    private void answerQuestion(String input) {
        if (this.player.getCurrentRoom() instanceof TaskRoom taskRoom) taskRoom.getTaskHandler().consume(input);
        else throw new AssertionError("How did you get here?");
    }

    public Player getPlayer() {
        return this.player;
    }

    public Menu getMenu() {
        return menu;
    }

    public StatusManager getStatusManager() {
        return this.status;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }
}