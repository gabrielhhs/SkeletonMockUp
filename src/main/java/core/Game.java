package core;

import commands.Command;
import commands.CommandManager;
import commands.GameCommands;
import items.GameItems;
import items.Item;
import rooms.Room;
import rooms.SpecialEventRoom;
import rooms.TaskRoom;
import rooms.TaskRoomWithEvent;
import saving.DataSaver;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Game implements Player.Observer {
    private final Room initialRoom = DataSeeder.generateRooms(this);
    private final Player player = new Player(this.initialRoom);
    public final Registry<Command> COMMANDS = GameCommands.register(new Registry<>());
    public final Registry<Item> ITEMS = GameItems.register(new Registry<>());
    private final CommandManager commandManager = new CommandManager(this, this.COMMANDS);
    private final InputStream in;
    private boolean running;
    private final Menu menu = new Menu(this);
    private final StatusManager status = new StatusManager();
    private final DataSaver saver;

    public Game(InputStream in, DataSaver saver) {
        this.in = in;
        this.saver = saver;
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
        if (input == null || input.isEmpty()) return;
        if (input.startsWith("/")) {handleCommand(input.substring(1)); return;}

        GameStatus status = this.status.get();
        if (status == null) throw new AssertionError("How did you get here?");

        switch (status) {
            case SELECTING_ROOM -> this.getPlayer().getCurrentRoom().consume(input);
            case IN_TASK, IN_HINT -> this.answerQuestion(input);
            case IN_OPTION -> this.menu.pauseMenuOptions(input);
            case IN_MAIN_MENU -> this.menu.mainMenuOptions(input);
            case IN_EVENT -> this.sendInputToEvent(input);
            default -> throw new IllegalStateException("Invalid room status");
        }
    }

    private void handleCommand(String input) {
        this.commandManager.executeCommand(input);
    }

    public Room getInitialRoom() {
        return this.initialRoom;
    }

    private void answerQuestion(String input) {
        if (this.player.getCurrentRoom() instanceof TaskRoom taskRoom) taskRoom.getTaskHandler().consume(input);
        else throw new AssertionError("How did you get here?");
    }

    private void sendInputToEvent(String input) {
        if (this.player.getCurrentRoom() instanceof TaskRoomWithEvent room) room.getEventHandler().consume(input);
        else if (this.player.getCurrentRoom() instanceof SpecialEventRoom room) room.getEventHandler().consume(input);
        else System.out.println("How did you get here?");
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