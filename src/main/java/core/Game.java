package core;

import rooms.Room;
import rooms.TaskRoom;

import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Room currentRoom = DataSeeder.seed(this);
    private Player player = DataSeeder.getPlayer(this.currentRoom);
    private final InputStream in;
    private boolean running;

    public Game(InputStream in) {
        this.in = in;
    }

    public void start() {
        //ToDo: assign starting status???
        this.currentRoom.enter();
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
            case IN_TASK -> answerQuestion(input);
            default -> throw new IllegalStateException("Invalid room status");
        }
    }

    private void handleCommand(String input) {
        switch (input) {
            case "status" -> System.out.printf("HP: %s%nScore: %s", this.player.getHealth(), this.player.getScore());
            case "kill" -> this.player.kill();
            default -> System.out.println("Invalid command");
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
}