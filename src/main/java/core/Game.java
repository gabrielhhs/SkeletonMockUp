package core;

import commands.CommandManager;
import rooms.Room;
import rooms.TaskRoom;
import saving.DataSaver;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Game implements Player.Observer {
    private final Room initialRoom = DataSeeder.generateRooms(this);
    private Player player = new Player(this.initialRoom);
    private CommandManager commandManager = new CommandManager(this);
    private final InputStream in;
    private boolean running;
    private Menu menu = new Menu(this);
    private final StatusManager status = new StatusManager();
    private final DataSaver saver;

    public Game(InputStream in, DataSaver saver) {
        this.in = in;
        this.saver = saver;
        this.commandManager.massRegisterCommand(DataSeeder.getCommands());
        this.player.addObserver(this);
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
            case IN_OPTION -> this.menu.pauseMenuOptions(input);
            case IN_MAIN_MENU -> this.menu.mainMenuOptions(input);
            default -> throw new IllegalStateException("Invalid room status");
        }
    }

    private void handleCommand(String input) {
        this.commandManager.executeCommand(input);
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

    private void collectRooms(Room self, Set<Room> result) {
        if (result.contains(self)) return;
        result.add(self);

        for (Room neighbor : self.getNeighboringRooms().values()) {
            this.collectRooms(neighbor, result);
        }
    }
    public Set<Room> collectRooms() {
        Set<Room> result = new HashSet<>();
        this.collectRooms(this.getInitialRoom(), result);
        return result;
    }

    public DataSaver getDataSaver() {
        return this.saver;
    }

    public void save(String saveName) {
        this.saver.save(this, saveName);
    }
    public void load(String saveName) {
        this.saver.load(this, saveName);
    }

    @Override
    public void onDeath(Player player) {
        this.stop();
    }
}