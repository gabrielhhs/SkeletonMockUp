package rooms;

import core.*;
import stratpattern.Task;
import stratpattern.TaskHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class Room {
    protected Game game;
    protected String name;
    protected final Map<String, Room> neighboringRooms = new HashMap<>();
    protected boolean isCleared = false;

    abstract void introductionText();
    abstract void handleUncleared();

    public void feedback() {
        System.out.println("You have survived...This time.");
        StringBuilder sb = new StringBuilder();
        String start = "In which door do you wish to go to. ";
        sb.append(start);
        for (Map.Entry<String, Room> entry : this.neighboringRooms.entrySet()){
            sb.append(" ").append(entry.getKey());
        }

        System.out.println(sb);

        String direction = new Scanner(System.in).nextLine();

        for (Map.Entry<String, Room> entry : this.neighboringRooms.entrySet()){
            if (direction.equals(entry.getKey())){
                this.game.goNext(entry.getValue());
            }
        }
    }

    public Room(Game game) {
        this.game = game;
    }

    public void addNeighboringRoom(String direction, Room room) {
        this.neighboringRooms.put(direction, room);
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public final void enter() {
        this.introductionText();
        if (!this.isCleared) {
            this.handleUncleared();
        }
        this.feedback();
    }

    public Map<String, Room> getNeighboringRooms() {
        return Collections.unmodifiableMap(this.neighboringRooms);
    }

    public void setCleared() {
        this.isCleared = true;
    }
}